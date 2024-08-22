package com.example.boda_server.domain.spot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spot {

    @Id
    @Column(name = "spot_id")
    private Long id;

    private String name;

    private String xCoord; //위도

    private String yCoord; //경도

    private String address;

    private String cityName;

    @Builder
    public Spot(Long id, String name, String xCoord, String yCoord, String address, String cityName) {
        this.id = id;
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.address = address;
        this.cityName = cityName;
    }
}
