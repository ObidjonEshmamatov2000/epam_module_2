package com.epam.esm.service;

import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.model.tag.Tag;
import com.epam.esm.service.tag.TagServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestTagService {

    @InjectMocks
    TagServiceImpl tagService;

    @Mock
    TagDao tagDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Junit 5 test for create tag method")
    public void testCreateTag() {
        Tag tag = new Tag(UUID.randomUUID(), "star");
        tagService.create(tag);

        Mockito.verify(tagDao, Mockito.times(1)).create(tag);
    }


    @Test
    @DisplayName("Junit 5 test for get all tags method")
    public void testGetAllTags() {
        List<Tag> list = new ArrayList<>();
        Tag tag1= new Tag(UUID.randomUUID(), "star");
        Tag tag2= new Tag(UUID.randomUUID(), "mafia");
        Tag tag3= new Tag(UUID.randomUUID(), "sun");

        list.add(tag1);
        list.add(tag2);
        list.add(tag3);

        Mockito.when(tagDao.getAll()).thenReturn(list);

        BaseResponseDto<List<Tag>> all = tagService.getAll();

        Assert.assertEquals(3, all.getData().size());
        Mockito.verify(tagDao, Mockito.times(1)).getAll();

    }

    @Test
    @DisplayName("Junit 5 test for get tag by id method")
    public void testGetTagById() {
        UUID id = UUID.randomUUID();

        Mockito.when(tagDao.get(id)).thenReturn(new Tag(id, "star"));

        BaseResponseDto<Tag> tag = tagService.get(id);
        Assert.assertEquals("star", tag.getData().getName());
    }
}
