package com.example.boda_server.domain.spot.dto.response;

import com.example.boda_server.domain.spot.entity.Spot;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotSearchResponse {
    private Long id;
    private String name;
    private String address;

    @Builder
    public SpotSearchResponse(Spot spot) {
        this.id = spot.getId();
        this.name = spot.getName();
        this.address = spot.getAddress();
    }
}
