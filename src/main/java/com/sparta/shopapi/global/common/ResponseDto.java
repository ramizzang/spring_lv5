package com.sparta.shopapi.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDto<T> {
    private Boolean status;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>(true, message, data);
    }
    public static <T> ResponseDto<T> success(String message) {
        return new ResponseDto<>(true, message, null);
    }
}
