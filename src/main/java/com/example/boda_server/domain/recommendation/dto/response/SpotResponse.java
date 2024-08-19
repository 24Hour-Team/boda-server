package com.example.boda_server.domain.recommendation.dto.response;

import com.example.boda_server.domain.recommendation.entity.Spot;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotResponse {
    private Long id;
    private String name;
    private String xCoord;
    private String yCoord;
    private String address;

    @Builder
    public SpotResponse(Spot spot) {
        this.address = spot.getAddress();
        this.yCoord = spot.getYCoord();
        this.xCoord = spot.getXCoord();
        this.name = spot.getName();
        this.id = spot.getId();
    }
}
