package com.example.boda_server.domain.auth.exception;

import com.example.boda_server.global.exception.BusinessException;
import lombok.Getter;

@Getter
public class AuthException extends BusinessException {
    private final AuthErrorCode authErrorCode;

    public AuthException(AuthErrorCode authErrorCode) {
        super(authErrorCode);
        this.authErrorCode = authErrorCode;
    }
}
