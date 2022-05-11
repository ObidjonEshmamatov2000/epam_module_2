package com.epam.esm.service;

import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.exception.BaseException;
import com.epam.esm.service.tag.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDao tagDao;

    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag(UUID.randomUUID(), "testTag");
    }

    @Test
    public void testCreateTagMethod() {
        given(tagDao.create(tag)).willReturn(tag);

        BaseResponseDto<Tag> tagBaseResponseDto = tagService.create(tag);

        assertEquals(201, tagBaseResponseDto.getStatus());
        assertEquals("success", tagBaseResponseDto.getMessage());
        Mockito.verify(tagDao, Mockito.times(1)).create(tag);
    }

    @Test
    public void testCreateTagMethodThrowsException() {
        given(tagDao.create(tag)).willReturn(null);

        assertThrows(BaseException.class, () -> {
            tagService.create(tag);
        });
        Mockito.verify(tagDao, Mockito.times(1)).create(tag);
    }

    @Test
    public void testGetTagById() {
        given(tagDao.get(tag.getId())).willReturn(tag);

        BaseResponseDto<Tag> tagBaseResponseDto = tagService.get(tag.getId());

        assertEquals(200, tagBaseResponseDto.getStatus());
        assertEquals("success", tagBaseResponseDto.getMessage());
        assertEquals("testTag", tagBaseResponseDto.getData().getName());

        Mockito.verify(tagDao, Mockito.times(1)).get(tag.getId());
    }

    @Test
    public void testGetTagByIdThrowsException() {
        given(tagDao.get(tag.getId())).willReturn(null);

        assertThrows(BaseException.class, () -> {
            tagService.get(tag.getId());
        });

        Mockito.verify(tagDao, Mockito.times(1)).get(tag.getId());
    }

    @Test
    public void testGetAllTags() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag(UUID.randomUUID(), "aaa");
        Tag tag2 = new Tag(UUID.randomUUID(), "bbb");
        Tag tag3 = new Tag(UUID.randomUUID(), "ccc");

        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        given(tagDao.getAll()).willReturn(tags);

        BaseResponseDto<List<Tag>> all = tagService.getAll();

        assertEquals(3, all.getData().size());
        assertEquals(200, all.getStatus());
        assertEquals("success", all.getMessage());
        Mockito.verify(tagDao, Mockito.times(1)).getAll();
    }

    @Test
    public void testDeleteTag() {
        given(tagDao.delete(tag.getId())).willReturn(1);

        BaseResponseDto<Tag> delete = tagService.delete(tag.getId());
        assertEquals(200, delete.getStatus());
        assertEquals("success", delete.getMessage());
        Mockito.verify(tagDao, Mockito.times(1)).delete(tag.getId());
    }

    @Test
    public void testDeleteTagThrowsException() {
        given(tagDao.delete(tag.getId())).willReturn(0);

        assertThrows(BaseException.class, () -> {
            tagService.delete(tag.getId());
        });
        Mockito.verify(tagDao, Mockito.times(1)).delete(tag.getId());
    }
}
