package com.example.boda_server.domain.recommendation.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AIRecommendRequest {
    private int gender;
    private int ageGrp;
    private int season;
    private int travelStyl1;
    private int travelStyl2;
    private int travelStyl3;
    private int travelStyl4;
    private int travelStyl5;
    private int travelStyl6;
    private int travelStyl7;
    private int travelStyl8;
    private String cityNm;

    @Builder
    public AIRecommendRequest(int gender, int ageGrp, int season, int travelStyl1, int travelStyl2, int travelStyl3, int travelStyl4, int travelStyl5, int travelStyl6, int travelStyl7, int travelStyl8, String cityNm) {
        this.gender = gender;
        this.ageGrp = ageGrp;
        this.season = season;
        this.travelStyl1 = travelStyl1;
        this.travelStyl2 = travelStyl2;
        this.travelStyl3 = travelStyl3;
        this.travelStyl4 = travelStyl4;
        this.travelStyl5 = travelStyl5;
        this.travelStyl6 = travelStyl6;
        this.travelStyl7 = travelStyl7;
        this.travelStyl8 = travelStyl8;
        this.cityNm = cityNm;
    }
}
