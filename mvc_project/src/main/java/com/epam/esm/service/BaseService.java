package com.epam.esm.service;

import com.epam.esm.dto.BaseResponseDto;

import java.util.UUID;

public interface BaseService<T, E> {
    BaseResponseDto<T> create(E e);
    BaseResponseDto<T> get(UUID id);
    BaseResponseDto<T> delete(UUID id);
}
