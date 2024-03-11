package com.sparta.shopapi.global.handler;

import com.sparta.shopapi.global.handler.exception.CustomApiException;
import com.sparta.shopapi.global.handler.exception.ErrorCode;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

import static com.sparta.shopapi.global.handler.exception.ErrorCode.INVALID_INPUT_VALUE;
import static com.sparta.shopapi.global.handler.exception.ErrorCode.INVALID_SIGNATURE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ErrorResponse> handleCustomApiException(CustomApiException e) {
        log.error("handleCustomApiException", e);
        final ErrorResponse response = ErrorResponse.of(e.getMessage());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(INVALID_INPUT_VALUE.getMessage(), e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // security exception

    //  아이디, password 이상
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception  e) {
        final ErrorResponse response = ErrorResponse.of("email or password is incorrect.");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ErrorResponse> handleAccountStatusException(AccountStatusException e) {
        final ErrorResponse response = ErrorResponse.of("User account is abnormal.");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException  e) {
        final ErrorResponse response = ErrorResponse.of("No permission");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // 엔드포인터 못 찾음
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException   e) {
        final ErrorResponse response = ErrorResponse.of("This API endpoint is not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


//     서버에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Exception e) {
        final ErrorResponse response = ErrorResponse.of("A server internal error occurs.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //JWT
    @ExceptionHandler({SecurityException.class,MalformedJwtException.class ,SignatureException.class})
    public ResponseEntity<ErrorResponse> handleJwtSignatureException(Exception e) {
        final ErrorResponse response = ErrorResponse.of(INVALID_SIGNATURE.getMessage()+"정상임");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


}
