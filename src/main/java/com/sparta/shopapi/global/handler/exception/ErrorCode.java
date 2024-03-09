package com.sparta.shopapi.global.handler.exception;

public enum ErrorCode {

    // common
    INVALID_INPUT_VALUE(" 입력한 값을 확인하세요."), // 벨리데이션 오류
    INTERNAL_SERVER_ERROR("Server Error"), // 서버 오류
    INVALID_TYPE_VALUE(" 입력한 값을 확인하세요."), // 입력한 값의 타입이 잘못
    HANDLE_ACCESS_DENIED(" Access is Denied"),

    // member
    EMAIL_DUPLICATION(" Email is Duplication"), // 중복된 이메일
    ADMIN_TOKEN_MISMATCH("관리자 토큰이 일치하지 않습니다."), // 관리자 가입
    ;

    private final String message;


    ErrorCode(final String message) {

        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
