package com.example.boda_server.domain.auth.exception;

import com.example.boda_server.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스입니다."),
    CANNOT_LOGOUT_OAUTH_SERVICE(HttpStatus.UNAUTHORIZED, "로그아웃을 진행할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}