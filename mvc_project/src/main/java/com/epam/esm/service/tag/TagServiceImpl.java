package com.epam.esm.service.tag;

import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        checkIfTagValid(tag);

        tag.setId(UUID.randomUUID());
        Tag create = tagDao.create(tag);

        if (create == null) {
            throw new BaseException(404, "failed to create tag");
        }

        return new BaseResponseDto<>(HttpStatus.CREATED.value(), "success", create);
    }

    private void checkIfTagValid(Tag tag) {
        if (tag == null || tag.getName() == null || tag.getName().trim().length() <= 0) {
            throw new BaseException(404, "unsatisfied tag name");
        }
    }

    @Override
    public BaseResponseDto<Tag> get(UUID id) {
        Tag tag = tagDao.get(id);

        if (tag == null) {
            throw new BaseException(404, "tag not found");
        }

        return new BaseResponseDto<>(HttpStatus.OK.value(), "success", tag);
    }

    @Override
    public BaseResponseDto<Tag> delete(UUID id) {
        int deleted = tagDao.delete(id);

        if (deleted != 1) {
            throw new BaseException(404, "failed to delete tag");
        }
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success");
    }

    @Override
    public BaseResponseDto<List<Tag>> getAll() {
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success", tagDao.getAll());
    }
}
