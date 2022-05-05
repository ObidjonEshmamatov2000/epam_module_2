package com.epam.esm.config;

import com.epam.esm.dto.BaseExceptionDto;
import com.epam.esm.dto.BaseResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> nullPointerExceptionHandler() {
        return ResponseEntity.ok(new BaseExceptionDto(0, "null pointer exception", 10500));
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<?> emptyResultDataExceptionHandler(){
        return ResponseEntity.ok(
                new BaseResponseDto<>(-1, "object not found")
        );
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationExceptionHandler(){
        return ResponseEntity.ok(
                new BaseResponseDto<>(-1, "this action violets foreign key constraint in PostgreSQL")
        );
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<?> classNotFoundExceptionHandler(Exception e){
        return ResponseEntity.ok(
                new BaseExceptionDto(500, e.getLocalizedMessage(), 10500)
        );
    }
}
