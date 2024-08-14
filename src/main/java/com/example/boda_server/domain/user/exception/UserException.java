package com.example.boda_server.domain.user.exception;

import com.example.boda_server.global.exception.BusinessException;
import lombok.Getter;

@Getter
public class UserException extends BusinessException {

    private final UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorcode) {
        super(userErrorcode);
        this.userErrorCode = userErrorcode;
    }
}
