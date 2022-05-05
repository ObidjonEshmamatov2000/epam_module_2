package com.epam.esm.service.tag;

import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.model.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService{

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public BaseResponseDto<Tag> create(Tag tag) {
        if (tag == null || tag.getName() == null) {
            return new BaseResponseDto<>(-1, "unknown tag name");
        }

        tag.setId(UUID.randomUUID());
        int create = tagDao.create(tag);

        if (create == 1) {
            return new BaseResponseDto<>(1, "success");
        }

        return new BaseResponseDto<>(0, "failed to create tag");

    }

    @Override
    public BaseResponseDto<Tag> get(UUID id) {
        Tag tag = tagDao.get(id);

        if (tag != null) {
            return new BaseResponseDto<>(1, "success", tag);
        }

        return new BaseResponseDto<>(0, "tag not found");
    }

    @Override
    public BaseResponseDto<Tag> delete(UUID id) {
        int deleted = tagDao.delete(id);

        if (deleted == 1) {
            return new BaseResponseDto<>(1, "success");
        }

        return new BaseResponseDto<>(0, "failed to delete");
    }

    @Override
    public BaseResponseDto<List<Tag>> getAll() {
        return new BaseResponseDto<>(1, "success", tagDao.getAll());
    }
}
