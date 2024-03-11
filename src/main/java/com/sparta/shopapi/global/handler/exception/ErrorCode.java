package com.sparta.shopapi.global.handler.exception;

public enum ErrorCode {

    // common
    INVALID_INPUT_VALUE(" 입력한 값을 확인하세요."), // 벨리데이션 오류
    INTERNAL_SERVER_ERROR("Server Error"), // 서버 오류
    HANDLE_ACCESS_DENIED(" Access is Denied"),

    // member
    EMAIL_DUPLICATION(" Email is Duplication"), // 중복된 이메일
    ADMIN_TOKEN_MISMATCH("관리자 토큰이 일치하지 않습니다."), // 관리자 가입

    //product
    NOT_FOUND_PRODUCT_ID("존재하지 않는 상품입니다."),
    INSUFFICIENT_STOCK("주문 가능 수량을 확인하세요."), // 재고 부족

    NOT_FOUND_CART_ID("장바구니에 상품이 존재하지 않습니다."),
    UNAUTHORIZED_ACCESS_TO_CART("장바구니에 접근할 권한이 없습니다."),

    //JWT
   INVALID_SIGNATURE("Invalid JWT signature"),
    EXPIRED_TOKEN("Expired JWT token"),
    UNSUPPORTED_TOKEN("Unsupported JWT token"),
    EMPTY_CLAIMS("JWT claims is empty");
    ;

    private final String message;


    ErrorCode(final String message) {

        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
