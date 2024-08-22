package com.example.boda_server.domain.spot.exception;

import com.example.boda_server.global.exception.BusinessException;
import lombok.Getter;

@Getter
public class SpotException extends BusinessException{
    private final SpotErrorCode spotErrorCode;

    public SpotException(SpotErrorCode spotErrorCode) {
        super(spotErrorCode);
        this.spotErrorCode = spotErrorCode;
    }
}
