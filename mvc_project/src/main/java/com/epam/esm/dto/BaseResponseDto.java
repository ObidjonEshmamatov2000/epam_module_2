package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"httpStatus", "responseMessage", "data"})
public class BaseResponseDto<T> {
    private int httpStatus;
    private String responseMessage;
    private T data;

    public BaseResponseDto(int httpStatus, String responseMessage) {
        this.httpStatus = httpStatus;
        this.responseMessage = responseMessage;
    }
}
