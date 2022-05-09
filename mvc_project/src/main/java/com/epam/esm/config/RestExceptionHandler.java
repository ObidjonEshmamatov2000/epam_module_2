package com.epam.esm.config;

import com.epam.esm.dto.BaseExceptionDto;
import com.epam.esm.dto.BaseResponseDto;
import com.epam.esm.exception.BaseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = NullPointerException.class)
//    public ResponseEntity<?> nullPointerExceptionHandler() {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new BaseExceptionDto(0, "null pointer exception"));
//    }
//
//    @ExceptionHandler(value = EmptyResultDataAccessException.class)
//    public ResponseEntity<?> emptyResultDataExceptionHandler(){
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new BaseResponseDto<>(404, "object not found"));
//    }
//
//    @ExceptionHandler(value = DataIntegrityViolationException.class)
//    public ResponseEntity<?> dataIntegrityViolationExceptionHandler(Exception e){
//        return ResponseEntity
//                .status(404)
//                .body(new BaseResponseDto<>(404, e.getLocalizedMessage()));
//    }
//
//    @ExceptionHandler(ClassNotFoundException.class)
//    public ResponseEntity<?> classNotFoundExceptionHandler(Exception e){
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new BaseExceptionDto(500, e.getLocalizedMessage()));
//    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> baseExceptionHandler(BaseException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new BaseExceptionDto(e.getStatus(), e.getMessage()));
    }
}
