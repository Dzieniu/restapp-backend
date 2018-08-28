package com.github.dzieniu2.controller;

import com.github.dzieniu2.exception.ErrorResponse;
import com.github.dzieniu2.exception.NoSuchFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchFieldException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchField(NoSuchFieldException exception) {
        return new ErrorResponse(404, exception.getMessage());
    }
}
