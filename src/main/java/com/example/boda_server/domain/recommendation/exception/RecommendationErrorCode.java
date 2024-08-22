package com.example.boda_server.domain.recommendation.exception;

import com.example.boda_server.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RecommendationErrorCode implements ErrorCode {
    SPOT_NOT_FOUND(HttpStatus.NOT_FOUND, "여행지를 찾을 수 없습니다."),
    TOUR_INFORMATION_NOT_FOUND(HttpStatus.NOT_FOUND, "여행 정보를 찾을 수 없습니다."),
    AI_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "AI 서버에서 오류가 발생했습니다."),
    UNAUTHORIZED_RECOMMENDATION_ACCESS(HttpStatus.FORBIDDEN, "해당 추천에 접근할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

