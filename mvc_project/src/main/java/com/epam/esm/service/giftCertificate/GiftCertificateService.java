package com.epam.esm.service.giftCertificate;

import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.service.BaseService;

import java.util.List;


public interface GiftCertificateService extends BaseService<GiftCertificate, GiftCertificateDto> {
    BaseResponseDto<List<GiftCertificateDto>> getFilteredGifts(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    );

    BaseResponseDto<List<GiftCertificate>> getAll();
    BaseResponseDto<GiftCertificate> update(GiftCertificateDto update);
}
