package com.sparta.shopapi.global.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    public CustomApiException(String message,  HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
