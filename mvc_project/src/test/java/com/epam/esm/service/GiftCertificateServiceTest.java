package com.epam.esm.service;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.service.giftCertificate.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceTest {
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private ModelMapper modelMapper;

    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;

    @BeforeEach
    public void setup() {
        giftCertificate = new GiftCertificate(
                        UUID.randomUUID(), "test", "test desc", 123.0, 23, "", "");
        giftCertificateDto = new GiftCertificateDto( "test", "test desc", 123.0, 23);
    }

    @Test
    public void testCreateGiftCertificate() {
        given(giftCertificateDao.create(giftCertificate)).willReturn(1);
        given(modelMapper.map(giftCertificateDto, GiftCertificate.class)).willReturn(giftCertificate);

        BaseResponseDto<GiftCertificate> dto = giftCertificateService.create(giftCertificateDto);

        assertEquals(1, dto.getHttpStatus());
        assertEquals("success", dto.getResponseMessage());
        verify(giftCertificateDao, times(1)).create(giftCertificate);
    }

    @Test
    public void testUpdateGiftCertificate() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate);
        given(modelMapper.getConfiguration()).willReturn(new InheritingConfiguration());
        doNothing().when(modelMapper).map(giftCertificateDto, giftCertificate);
        giftCertificateDto.setId(giftCertificate.getId());

        given(giftCertificateDao.update(giftCertificate)).willReturn(1);
        BaseResponseDto<GiftCertificate> update = giftCertificateService.update(giftCertificateDto);

        assertEquals(1, update.getHttpStatus());
        assertEquals("success", update.getResponseMessage());
        verify(giftCertificateDao, times(1)).get(giftCertificate.getId());
        verify(giftCertificateDao, times(1)).update(giftCertificate);
    }

    @Test
    public void  testGetGiftCertificateById() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate);

        BaseResponseDto<GiftCertificate> dto = giftCertificateService.get(giftCertificate.getId());

        assertEquals(1, dto.getHttpStatus());
        assertEquals("success", dto.getResponseMessage());
        assertEquals("test", dto.getData().getName());
        verify(giftCertificateDao, times(1)).get(giftCertificate.getId());
    }

    @Test
    public void testGetAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate gc1 =
                new GiftCertificate(UUID.randomUUID(), "a", "aa", 12.0, 2, "", "");
        GiftCertificate gc2 =
                new GiftCertificate(UUID.randomUUID(), "b", "bb", 12.0, 2, "", "");
        GiftCertificate gc3 =
                new GiftCertificate(UUID.randomUUID(), "c", "cc", 12.0, 2, "", "");

        giftCertificates.add(gc1);
        giftCertificates.add(gc2);
        giftCertificates.add(gc3);

        given(giftCertificateDao.getAll()).willReturn(giftCertificates);

        BaseResponseDto<List<GiftCertificate>> all = giftCertificateService.getAll();

        assertEquals(3, all.getData().size());
        assertEquals(1, all.getHttpStatus());
        assertEquals("success", all.getResponseMessage());
        verify(giftCertificateDao, times(1)).getAll();
    }

    @Test
    public void testDeleteGiftCertificate() {
        given(giftCertificateDao.delete(giftCertificate.getId())).willReturn(1);

        BaseResponseDto<GiftCertificate> delete = giftCertificateService.delete(giftCertificate.getId());

        assertEquals(1, delete.getHttpStatus());
        assertEquals("success", delete.getResponseMessage());
        verify(giftCertificateDao, times(1)).delete(giftCertificate.getId());
    }

}
