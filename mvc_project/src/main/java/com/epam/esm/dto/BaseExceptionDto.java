package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseExceptionDto{
    private Integer status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    public BaseExceptionDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
