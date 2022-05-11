package com.epam.esm.service;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.domain.giftCertificate.GiftCertificate;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BaseException;
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

import static org.junit.jupiter.api.Assertions.*;
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
        given(modelMapper.map(giftCertificate, GiftCertificateDto.class)).willReturn(giftCertificateDto);

        BaseResponseDto<GiftCertificateDto> dto = giftCertificateService.create(giftCertificateDto);
        GiftCertificateDto data = dto.getData();
        assertEquals(201, dto.getStatus());
        assertEquals(giftCertificate.getName(), data.getName());
        assertEquals(giftCertificate.getDescription(), data.getDescription());
        assertEquals(giftCertificate.getPrice(), data.getPrice());
        assertEquals(giftCertificate.getDuration(), data.getDuration());
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
        verify(giftCertificateDao, times(1)).get(giftCertificate.getId());
        verify(giftCertificateDao, times(1)).update(giftCertificate);
    }

    @Test
    public void testUpdateGiftCertificateThrowsException() {
        given(giftCertificateDao.get(giftCertificate.getId())).willReturn(giftCertificate); //old
        given(modelMapper.getConfiguration()).willReturn(new InheritingConfiguration());
        doNothing().when(modelMapper).map(giftCertificateDto, giftCertificate);
        giftCertificateDto.setId(giftCertificate.getId());

        given(giftCertificateDao.update(giftCertificate)).willReturn(0);

        assertThrows(BaseException.class, () -> giftCertificateService.update(giftCertificateDto));
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
        assertEquals(giftCertificate.getName(), dto.getData().getName());
        assertEquals(giftCertificate.getDescription(), dto.getData().getDescription());
        assertEquals(giftCertificate.getPrice(), dto.getData().getPrice());
        assertEquals(giftCertificate.getDuration(), dto.getData().getDuration());
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
    public void testFilteredGiftCertificates() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate gc2 =
                new GiftCertificate(UUID.randomUUID(), "bc", "bb", BigDecimal.valueOf(12.0), 2, LocalDateTime.now(), LocalDateTime.now(), null);
        GiftCertificate gc3 =
                new GiftCertificate(UUID.randomUUID(), "c", "cc", BigDecimal.valueOf(12.0), 2, LocalDateTime.now(), LocalDateTime.now(), null);

//        giftCertificates.add(gc2);
//        giftCertificates.add(gc3);

        given(giftCertificateDao.getFilteredGifts("c", null, false, false, false)).willReturn(giftCertificates);


        BaseResponseDto<List<GiftCertificateDto>> all =
                giftCertificateService.getFilteredGifts("c", null, false, false, false);

//        assertEquals(0, all.getData().size());
        assertEquals(200, all.getStatus());
        assertEquals("no certificates found", all.getMessage());
        verify(giftCertificateDao, times(1)).getFilteredGifts("c", null, false, false, false );
    }

    @Test
    public void testDeleteGiftCertificate() {
        given(giftCertificateDao.delete(giftCertificate.getId())).willReturn(1);

        BaseResponseDto<GiftCertificateDto> delete = giftCertificateService.delete(giftCertificate.getId());

        assertEquals(200, delete.getStatus());
        assertEquals("success", delete.getMessage());
        verify(giftCertificateDao, times(1)).delete(giftCertificate.getId());
    }

    @Test
    public void testDeleteGiftCertificateThrowsException() {
        given(giftCertificateDao.delete(giftCertificate.getId())).willReturn(0);

        assertThrows(BaseException.class, () -> giftCertificateService.delete(giftCertificate.getId()));
        verify(giftCertificateDao, times(1)).delete(giftCertificate.getId());
    }
}
