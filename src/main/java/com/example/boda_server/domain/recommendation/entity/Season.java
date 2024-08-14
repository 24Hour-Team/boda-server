package com.example.boda_server.domain.recommendation.entity;

public enum Season {
    SPRING(0),
    SUMMER(1),
    FALL(2),
    WINTER(3);

    private final int seasonNum;

    Season(int seasonNum) {
        this.seasonNum = seasonNum;
    }
}
