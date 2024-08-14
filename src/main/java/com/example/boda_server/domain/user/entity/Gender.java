package com.example.boda_server.domain.user.entity;

public enum Gender {
    MALE(0),
    FEMALE(1);

    private final int genderNum;

    Gender(int genderNum) {
        this.genderNum = genderNum;
    }
}
