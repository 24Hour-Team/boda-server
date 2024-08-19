package com.example.boda_server.domain.recommendation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    @Column(name = "spot_id")
    private Long id;

    private String name;

    private String xCoord; //위도

    private String yCoord; //경도

    private String address;

    @Builder
    public Spot(String name, String xCoord, String yCoord, String address) {
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.address = address;
    }
}
