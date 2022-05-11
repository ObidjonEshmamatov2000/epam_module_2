package com.epam.esm.service.tag;

import com.epam.esm.dao.tag.TagDao;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.domain.tag.Tag;
import com.epam.esm.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
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
            log.info("failed to create tag with name = " + tag.getName(), tag);
            throw new BaseException(400, "failed to create tag");
        }

        log.info("tag with name = " + tag.getName() + " successfully created");
        return new BaseResponseDto<>(HttpStatus.CREATED.value(), "success", create);
    }

    private void checkIfTagValid(Tag tag) {
        if (tag == null || tag.getName() == null || tag.getName().trim().length() <= 0) {
            throw new BaseException(400, "unsatisfied tag name");
        }
    }

    @Override
    public BaseResponseDto<Tag> get(UUID id) {
        Tag tag = tagDao.get(id);

        if (tag == null) {
            log.info("tag with id = " + id + " is not found in the database");
            throw new BaseException(400, "tag not found");
        }

        log.info("tag with id " + id + " is send to the client");
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success", tag);
    }

    @Override
    public BaseResponseDto<Tag> delete(UUID id) {
        int deleted = tagDao.delete(id);

        if (deleted != 1) {
            log.info("tag with id" + id + " is not present in the database");
            throw new BaseException(400, "failed to delete tag");
        }

        log.info("tag with id " + id + " is deleted successfully");
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success");
    }

    @Override
    public BaseResponseDto<List<Tag>> getAll() {
        log.info("all tags send to the user");
        return new BaseResponseDto<>(HttpStatus.OK.value(), "success", tagDao.getAll());
    }
}
