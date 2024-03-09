package com.sparta.shopapi.global.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.shopapi.global.handler.exception.ErrorCode.INVALID_TYPE_VALUE;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private boolean status = false;
    private String message;
    private List<FieldError> errors;


    private ErrorResponse(final String message, final List<FieldError> errors) {

        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.errors = new ArrayList<>();
    }


    public static ErrorResponse of(final String message, final BindingResult bindingResult) {
        return new ErrorResponse(message, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of(final String message, final List<FieldError> errors) {
        return new ErrorResponse(message, errors);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(INVALID_TYPE_VALUE.getMessage(), errors);
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            // 객체 초기화
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            // 매개변수로 초기화 된 FieldError 리스트 반환
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            // BindingResult 에서 발생한 모든 필드 에러 가져오기
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError( // 각각의 필드 에러를 FieldError 객체로 매핑
                            error.getField(), // 에러가 발생한 필드 
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(), // 필드의 value 값에 대한 string 값 null 이면 빈 문자열로
                            error.getDefaultMessage())) // field error의 기본 메세지
                    .collect(Collectors.toList()); // 리스트로 변환
        }
    }
}
