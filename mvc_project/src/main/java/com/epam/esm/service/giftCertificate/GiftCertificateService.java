package com.epam.esm.service.giftCertificate;

import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.BaseService;

import java.util.List;


public interface GiftCertificateService extends BaseService<GiftCertificateDto> {
    BaseResponseDto<List<GiftCertificateDto>> getAll();
    BaseResponseDto<GiftCertificateDto> update(GiftCertificateDto update);
    BaseResponseDto<List<GiftCertificateDto>> getByTagName(String tagName);
}
