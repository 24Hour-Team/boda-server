package com.example.boda_server.domain.recommendation.exception;

import com.example.boda_server.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecommendationErrorCode implements ErrorCode {
    SPOT_NOT_FOUND(HttpStatus.BAD_REQUEST, "여행지를 찾을 수 없습니다."),
    TOUR_INFORMATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "여행 정보를 찾을 수 없습니다."),
    AI_SERVER_EXCEPTION(HttpStatus.BAD_REQUEST, "AI 서버에서 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
