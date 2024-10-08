package com.example.boda_server.domain.spot.dto.response;

import com.example.boda_server.domain.spot.entity.Spot;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotResponse {
    private Long id;
    private String name;
    private String xCoord; //위도
    private String yCoord; //경도
    private String address;
    private String cityName;

    @Builder
    public SpotResponse(Spot spot) {
        this.id = spot.getId();
        this.name = spot.getName();
        this.xCoord = spot.getXCoord();
        this.yCoord = spot.getYCoord();
        this.address = spot.getAddress();
        this.cityName = spot.getCityName();
    }
}
