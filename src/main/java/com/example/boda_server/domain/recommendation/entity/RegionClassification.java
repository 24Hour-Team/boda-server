package com.example.boda_server.domain.recommendation.entity;

import lombok.Getter;

@Getter
public enum RegionClassification {
    SEOUL("서울특별시"),
    GANGNEUNG("강릉시"),
    SUWON("수원시"),
    GAPYEONG("가평군"),
    DAEJEON("대전광역시"),
    INCHEON("인천광역시"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    CHUNCHEON("춘천시"),
    JEJU("제주시"),
    GYEONGJU("경주시"),
    GONGJU("공주시"),
    SEOGWIPO("서귀포시"),
    JEONJU("전주시"),
    GUNSAN("군산시"),
    GWANGJU("광주광역시"),
    SUNCHEON("순천시"),
    SOKCHO("속초시"),
    YANGYANG("양양군"),
    DANYANG("단양군"),
    YEOSU("여수시"),
    TAEAN("태안군");


    private final String label;

    RegionClassification(String label) {
        this.label = label;
    }
}
