package com.example.boda_server.domain.recommendation.dto.response;

import com.example.boda_server.domain.recommendation.entity.TourInformation;
import com.example.boda_server.domain.spot.dto.response.SpotResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationResponse {
    private LocalDateTime createdDateTime;
    private List<SpotResponse> spotResponses;

    @Builder
    public RecommendationResponse(TourInformation tourInformation) {
        this.createdDateTime = tourInformation.getCreatedDateTime();
        this.spotResponses = tourInformation.getRecommendedSpots().stream()
                .map(recommendedSpot -> SpotResponse.builder()
                        .spot(recommendedSpot.getSpot())
                        .build())
                .toList();
    }
}
