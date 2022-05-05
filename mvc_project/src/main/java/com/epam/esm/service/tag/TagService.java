package com.epam.esm.service.tag;

import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.model.tag.Tag;
import com.epam.esm.service.BaseService;

import java.util.List;

public interface TagService extends BaseService<Tag> {
    BaseResponseDto<List<Tag>> getAll();
}
