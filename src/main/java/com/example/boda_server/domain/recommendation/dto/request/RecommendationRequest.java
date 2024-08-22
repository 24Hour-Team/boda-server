package com.example.boda_server.domain.recommendation.dto.request;

import com.example.boda_server.domain.recommendation.entity.RegionClassification;
import com.example.boda_server.domain.recommendation.entity.Season;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationRequest {
    private Season season;
    private RegionClassification regionClassification;
    private Boolean travelStyle1; //자연 vs 도시
    private Boolean travelStyle2; //숙박 vs 당일
    private Boolean travelStyle3; //새로운 vs 익숙한
    private Boolean travelStyle4; //편함비쌈숙소 vs 불편함저렴함숙소
    private Boolean travelStyle5; //휴양휴식 vs 엑티비티
    private Boolean travelStyle6; //안유명한 vs 유명한
    private Boolean travelStyle7; //계획 vs 상황
    private Boolean travelStyle8; //사진중요 vs 사진안중요

    public RecommendationRequest(Season season, RegionClassification regionClassification, Boolean travelStyle1, Boolean travelStyle2, Boolean travelStyle3, Boolean travelStyle4, Boolean travelStyle5, Boolean travelStyle6, Boolean travelStyle7, Boolean travelStyle8) {
        this.season = season;
        this.regionClassification = regionClassification;
        this.travelStyle1 = travelStyle1;
        this.travelStyle2 = travelStyle2;
        this.travelStyle3 = travelStyle3;
        this.travelStyle4 = travelStyle4;
        this.travelStyle5 = travelStyle5;
        this.travelStyle6 = travelStyle6;
        this.travelStyle7 = travelStyle7;
        this.travelStyle8 = travelStyle8;
    }
}
