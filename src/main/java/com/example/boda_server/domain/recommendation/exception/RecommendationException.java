package com.example.boda_server.domain.recommendation.exception;

import com.example.boda_server.global.exception.BusinessException;
import lombok.Getter;

@Getter
public class RecommendationException extends BusinessException {

    private final RecommendationErrorCode recommendationErrorCode;

    public RecommendationException(RecommendationErrorCode recommendationErrorCode) {
        super(recommendationErrorCode);
        this.recommendationErrorCode = recommendationErrorCode;
    }
}
