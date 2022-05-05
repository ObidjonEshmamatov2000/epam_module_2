package com.epam.esm.service.giftCertificate;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        giftCertificateDto.setCreateDate(getCurrentTimeInIso8601());
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
    public BaseResponseDto<List<GiftCertificateDto>> getAll() {
        List<GiftCertificate> all = giftCertificateDao.getAll();

        return new BaseResponseDto<List<GiftCertificateDto>>(1, "success", convertToDto(all));
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

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(update, old);

        int result = giftCertificateDao.update(old);

        if (result == 1) {
            createTags(update);
            return new BaseResponseDto<>(1, "success");
        }
        return new BaseResponseDto<>(0, "failed to update");
    }

    @Override
    public BaseResponseDto<List<GiftCertificateDto>> getByTagName(String tagName) {
        List<GiftCertificate> byTagName = giftCertificateDao.getByTagName(tagName);

        if (byTagName.size() == 0 ) {
            return new BaseResponseDto<>(0, "no gift certificate found");
        }


        return new BaseResponseDto<>(1, "success");
    }

    @JsonIgnore
    public String getCurrentTimeInIso8601() {
        return ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT );
    }
}
