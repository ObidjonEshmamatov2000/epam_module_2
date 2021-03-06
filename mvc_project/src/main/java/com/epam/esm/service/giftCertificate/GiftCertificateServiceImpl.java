package com.epam.esm.service.giftCertificate;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.domain.giftCertificate.GiftCertificate;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    @Transactional
    public BaseResponseDto<GiftCertificateDto> create(GiftCertificateDto giftCertificateDto) {
        checkIfGiftCertificateValid(giftCertificateDto);

        giftCertificateDto.setCreateDate(getCurrentTimeInIso8601());
        giftCertificateDto.setLastUpdateDate(getCurrentTimeInIso8601());
        giftCertificateDto.setId(UUID.randomUUID());
        GiftCertificate map = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        GiftCertificate created = giftCertificateDao.create(map);

        createTags(giftCertificateDto);
        GiftCertificateDto certificateDto = modelMapper.map(created, GiftCertificateDto.class);
        log.info("gift certificate with name " + giftCertificateDto.getName() + " is created in the database");
        return new BaseResponseDto<>(HttpStatus.CREATED.value(), "success", certificateDto);
    }

    private void checkIfGiftCertificateValid(GiftCertificateDto gc) {
        if (gc == null || gc.getName() == null || gc.getName().trim().length() == 0) {
            log.info("gift certificate with name is not acceptable");
            throw new BaseException(400, "unsatisfied git certificate name");
        }

        if (
                (gc.getDuration()!= null && gc.getDuration() < 0)
                        || (gc.getPrice() != null && gc.getPrice().compareTo(BigDecimal.ZERO) < 0)
        ) {
            log.info("price or duration of gift certificate is not acceptable");
            throw new BaseException(400, "price or duration is not preferable");
        }
    }

    private void createTags(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getTags() != null && giftCertificateDto.getTags().size() != 0) {
            giftCertificateDao.createTagsWithGiftCertificate(giftCertificateDto.getId(), giftCertificateDto.getTags());
        }
    }

    @Override
    public BaseResponseDto<GiftCertificateDto> get(UUID id) {
        GiftCertificate giftCertificate = giftCertificateDao.get(id);
        GiftCertificateDto certificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
        log.info("gift certificate with id " + id + " is sent to the user successfully");
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success", certificateDto);
    }

    @Override
    public BaseResponseDto<GiftCertificateDto> delete(UUID id) {
        int delete = giftCertificateDao.delete(id);

        if (delete != 1) {
            log.info("gift certificate with id " + id + " is not found in the database");
            throw new BaseException(400, "failed to delete gift certificate with id " + id);
        }
        log.info("gift certificate with id " + id + " is deleted successfully");
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success");
    }


    @Override
    public BaseResponseDto<List<GiftCertificateDto>> getAll() {
        List<GiftCertificate> all = giftCertificateDao.getAll();
        log.info("all gift certificates send to the user");
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success", convertToDto(all));
    }

    List<GiftCertificateDto> convertToDto(List<GiftCertificate> certificates) {
        return certificates.stream()
                .map((certificate) ->
                        modelMapper.map(certificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BaseResponseDto<List<GiftCertificateDto>> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    ) {
        List<GiftCertificate> filteredGifts = giftCertificateDao.getFilteredGifts(
                searchWord, tagName, doNameSort, doDateSort, isDescending);

        if (filteredGifts.size() == 0) {
            log.info("no gift certificate matches given filter arguments");
            return new BaseResponseDto<>(HttpStatus.OK.value(), "no certificates found");
        }

        return new BaseResponseDto<>(HttpStatus.OK.value(), "success",
                convertToDto(addTagsToGiftCertificates(filteredGifts)));
    }

    private List<GiftCertificate> addTagsToGiftCertificates(List<GiftCertificate> giftCertificateDtos) {
        return giftCertificateDtos.stream().peek(certificate ->
                        certificate.setTags(tagDao.getGiftCertificateWithTags(certificate.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BaseResponseDto<GiftCertificateDto> update(GiftCertificateDto update) {
        GiftCertificate old = giftCertificateDao.get(update.getId());
        update.setLastUpdateDate(getCurrentTimeInIso8601());
        checkIfGiftCertificateValid(update);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(update, old);

        int result = giftCertificateDao.update(old);
        if (result != 1) {
            log.info("gift certificate with id " + update.getId() + " is not updated in the database");
            throw new BaseException(500, "failed to update");
        }
        createTags(update);
        log.info("gift certificate with id " + update.getId() + " is successfully updated in the database");
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success");
    }

    public LocalDateTime getCurrentTimeInIso8601() {
        return ZonedDateTime.now( ZoneOffset.UTC ).toLocalDateTime();
    }
}
