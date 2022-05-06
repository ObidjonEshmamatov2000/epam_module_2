package com.epam.esm.service.giftCertificate;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.model.tag.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService{

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, ModelMapper modelMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public BaseResponseDto<GiftCertificateDto> create(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto == null || giftCertificateDto.getName() == null) {
            return new BaseResponseDto<>(-1, "unknown git certificate name");
        }

        giftCertificateDto.setCreateDate(getCurrentTimeInIso8601());
        giftCertificateDto.setLastUpdateDate(getCurrentTimeInIso8601());
        giftCertificateDto.setId(UUID.randomUUID());

        int created = giftCertificateDao.create(modelMapper.map(giftCertificateDto, GiftCertificate.class));

        if (created == 1) {
            createTags(giftCertificateDto);
            return new BaseResponseDto<>(1, "success");
        }
        return new BaseResponseDto<>(0, "failed to create gift certificate");
    }

    private void createTags(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getTags() != null && giftCertificateDto.getTags().size() != 0) {
            tagDao.createWithGiftCertificate(giftCertificateDto.getId(), giftCertificateDto.getTags());
        }
    }

    @Override
    public BaseResponseDto<GiftCertificateDto> get(UUID id) {
        GiftCertificate giftCertificate = giftCertificateDao.get(id);
        GiftCertificateDto map = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        return new BaseResponseDto<>(1, "success", map);
    }

    @Override
    public BaseResponseDto<GiftCertificateDto> delete(UUID id) {
        int delete = giftCertificateDao.delete(id);

        if (delete == 1) {
            return new BaseResponseDto<>(1, "success");
        }
        return new BaseResponseDto<>(0, "failed to delete gift certificate");
    }


    @Override
    public BaseResponseDto<List<GiftCertificateDto>> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    ) {

        List<GiftCertificate> certificateList = getCertificateList(searchWord, tagName);
        List<GiftCertificateDto> giftCertificateDtos = convertToDto(sortCertificateList(
                doNameSort, doDateSort, isDescending, certificateList
        ));
        return new BaseResponseDto<>(1, "success", giftCertificateDtos);
    }

    @Override
    public BaseResponseDto<List<GiftCertificateDto>> getAll() {
        List<GiftCertificate> all = giftCertificateDao.getAll();
        return new BaseResponseDto<>(1, "success", convertToDto(all));
    }

    private List<GiftCertificate> getCertificateList(String searchWord, String tagName) {
        if (searchWord != null) {
            if (tagName != null) {
                tagName = tagName.toLowerCase();
                return giftCertificateDao.searchAndGetByTagName(searchWord, tagName);
            }
            return giftCertificateDao.searchByNameOrDescription(searchWord);
        } else if (tagName != null)
            return giftCertificateDao.getByTagName(tagName);

        return giftCertificateDao.getAll();
    }

    private List<GiftCertificate> sortCertificateList(
            boolean doNameSort, boolean doDateSort, boolean isDescending, List<GiftCertificate> certificateList) {

        if (doNameSort) {
            if (doDateSort)
                certificateList.sort(
                        Comparator.comparing(GiftCertificate::getName)
                                .thenComparing(c -> ZonedDateTime.parse(c.getCreateDate())));
            else
                certificateList.sort(Comparator.comparing(GiftCertificate::getName));
        } else if (doDateSort)
            certificateList.sort(
                    Comparator.comparing((GiftCertificate c) -> ZonedDateTime.parse(c.getCreateDate())));

        if(isDescending)
            Collections.reverse(certificateList);

        return certificateList;
    }

    private List<GiftCertificateDto> convertToDto(List<GiftCertificate> certificates) {
        return certificates.stream()
                .map((certificate) ->
                        modelMapper.map(certificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BaseResponseDto<GiftCertificateDto> update(GiftCertificateDto update) {
        GiftCertificate old = giftCertificateDao.get(update.getId());
        update.setLastUpdateDate(getCurrentTimeInIso8601());

        if (update.getDuration() <= 0)
        update.setDuration(old.getDuration());

        if (update.getPrice() <= 0)
        update.setPrice(old.getPrice());

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(update, old);

        int result = giftCertificateDao.update(old);

        if (result == 1) {
            createTags(update);
            return new BaseResponseDto<>(1, "success");
        }
        return new BaseResponseDto<>(0, "failed to update");
    }

    public String getCurrentTimeInIso8601() {
        return ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT );
    }
}
