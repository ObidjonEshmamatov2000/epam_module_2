package com.epam.esm.service.giftCertificate;

import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.BaseService;

import java.util.List;


public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    BaseResponseDto<List<GiftCertificateDto>> getAll(
            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending
    );
    BaseResponseDto<GiftCertificateDto> update(GiftCertificateDto update);
}
