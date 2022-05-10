package com.epam.esm.service;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.domain.giftCertificate.GiftCertificate;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.giftCertificate.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                        UUID.randomUUID(),
                "test",
                "test desc",
                BigDecimal.valueOf(123.0),
                23, LocalDateTime.now(), LocalDateTime.now(), null);
        giftCertificateDto = new GiftCertificateDto( "test", "test desc", BigDecimal.valueOf(123.0), 23);
    }

    @Test
    public void testCreateGiftCertificate() {
        given(giftCertificateDao.create(giftCertificate)).willReturn(giftCertificate);
        given(modelMapper.map(giftCertificateDto, GiftCertificate.class)).willReturn(giftCertificate);

        BaseResponseDto<GiftCertificateDto> dto = giftCertificateService.create(giftCertificateDto);

        assertEquals(201, dto.getStatus());
        assertEquals("success", dto.getMessage());
        verify(giftCertificateDao, times(1)).create(giftCertificate);
    }

    @Test
    public void testUpdateGiftCertificate() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate);
        given(modelMapper.getConfiguration()).willReturn(new InheritingConfiguration());
        doNothing().when(modelMapper).map(giftCertificateDto, giftCertificate);
        giftCertificateDto.setId(giftCertificate.getId());

        given(giftCertificateDao.update(giftCertificate)).willReturn(1);
        BaseResponseDto<GiftCertificateDto> update = giftCertificateService.update(giftCertificateDto);

        assertEquals(200, update.getStatus());
        assertEquals("success", update.getMessage());
        verify(giftCertificateDao, times(1)).get(giftCertificate.getId());
        verify(giftCertificateDao, times(1)).update(giftCertificate);
    }

    @Test
    public void  testGetGiftCertificateById() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate);
        given(modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .willReturn(giftCertificateDto);
        BaseResponseDto<GiftCertificateDto> dto = giftCertificateService.get(giftCertificate.getId());

        assertEquals(200, dto.getStatus());
        assertEquals("success", dto.getMessage());
        assertEquals("test", dto.getData().getName());
        verify(giftCertificateDao, times(1)).get(giftCertificate.getId());
    }

    @Test
    public void testGetAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate gc1 =
                new GiftCertificate(UUID.randomUUID(), "a", "aa", BigDecimal.valueOf(12.0), 2, LocalDateTime.now(), LocalDateTime.now(), null);
        GiftCertificate gc2 =
                new GiftCertificate(UUID.randomUUID(), "b", "bb", BigDecimal.valueOf(12.0), 2, LocalDateTime.now(), LocalDateTime.now(), null);
        GiftCertificate gc3 =
                new GiftCertificate(UUID.randomUUID(), "c", "cc", BigDecimal.valueOf(12.0), 2, LocalDateTime.now(), LocalDateTime.now(), null);

        giftCertificates.add(gc1);
        giftCertificates.add(gc2);
        giftCertificates.add(gc3);

        given(giftCertificateDao.getAll()).willReturn(giftCertificates);

        BaseResponseDto<List<GiftCertificateDto>> all = giftCertificateService.getAll();

        assertEquals(3, all.getData().size());
        assertEquals(200, all.getStatus());
        assertEquals("success", all.getMessage());
        verify(giftCertificateDao, times(1)).getAll();
    }

    @Test
    public void testDeleteGiftCertificate() {
        given(giftCertificateDao.delete(giftCertificate.getId())).willReturn(1);

        BaseResponseDto<GiftCertificateDto> delete = giftCertificateService.delete(giftCertificate.getId());

        assertEquals(200, delete.getStatus());
        assertEquals("success", delete.getMessage());
        verify(giftCertificateDao, times(1)).delete(giftCertificate.getId());
    }

}
