package com.example.boda_server.domain.recommendation.exception;

import com.example.boda_server.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecommendationErrorCode implements ErrorCode {
    SPOT_NOT_FOUND(HttpStatus.BAD_REQUEST, "여행지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
