package com.example.boda_server.domain.recommendation.dto.response;

import com.example.boda_server.domain.recommendation.entity.Spot;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationResponse {
    private Long spotId;
    private String name;
    private String xCoord; //위도
    private String yCoord; //경도
    private String address;
    private String cityName;

    @Builder
    public RecommendationResponse(Spot spot) {
        this.spotId = spot.getId();
        this.name = spot.getName();
        this.xCoord = spot.getXCoord();
        this.yCoord = spot.getYCoord();
        this.address = spot.getAddress();
        this.cityName = spot.getCityName();
    }
}
