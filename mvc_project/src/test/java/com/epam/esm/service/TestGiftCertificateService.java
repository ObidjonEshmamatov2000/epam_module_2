package com.epam.esm.service;

import com.epam.esm.dao.giftCertificate.GiftCertificateDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.service.giftCertificate.GiftCertificateServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestGiftCertificateService {

    @InjectMocks
    GiftCertificateServiceImpl service;

    @Mock
    GiftCertificateDao dao;

    @Mock
    ModelMapper mapper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificate gc1 = new GiftCertificate(UUID.randomUUID(), "a",
                "a", 12, 1, "", "");
        GiftCertificate gc2 = new GiftCertificate(UUID.randomUUID(), "b",
                "b", 13, 1, "", "");
        GiftCertificate gc3 = new GiftCertificate(UUID.randomUUID(), "c",
                "c", 14, 1, "", "");

        giftCertificates.add(gc1);
        giftCertificates.add(gc2);
        giftCertificates.add(gc3);

        when(dao.getAll()).thenReturn(giftCertificates);

        //test
        BaseResponseDto<List<GiftCertificateDto>> all =
                service.getFilteredGifts("", "", false, false, false);
        assertEquals(3, all.getData().size());

        verify(dao, times(1)).getAll();
    }

    @Test
    public void testGetById() {
        UUID id = UUID.randomUUID();
        when(dao.get(id)).thenReturn(new GiftCertificate(id, "a", "aa", 12, 1, "", ""));

        GiftCertificateDto dto = service.get(id).getData();

        GiftCertificate map = mapper.map(dto, GiftCertificate.class);
        assertEquals("a", map.getName());
        assertEquals("aa", map.getDescription());
//        assertEquals(12, dto.getPrice());
        assertEquals(1, map.getDuration());
        assertEquals("", map.getCreateDate());
        assertEquals("", map.getLastUpdateDate());
    }

    private GiftCertificateDto convertToDto(GiftCertificate gc) {
        return new GiftCertificateDto(gc.getName(), gc.getDescription(), gc.getPrice(), gc.getDuration());
    }
}
