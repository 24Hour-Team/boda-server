package com.example.boda_server.domain.user.entity;

import lombok.Getter;

@Getter
public enum AgeRange {
    TWENTIES(20), //20대 이하
    THIRTIES(30),
    FORTIES(40),
    FIFTIES(50); //50대 이상

    private final int ageNum;

    AgeRange(int ageNum) {
        this.ageNum = ageNum;
    }
}
