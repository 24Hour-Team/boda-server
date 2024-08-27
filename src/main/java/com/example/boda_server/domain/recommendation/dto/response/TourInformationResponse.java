package com.example.boda_server.domain.recommendation.dto.response;

import com.example.boda_server.domain.recommendation.entity.RegionClassification;
import com.example.boda_server.domain.recommendation.entity.Season;
import com.example.boda_server.domain.recommendation.entity.TourInformation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourInformationResponse {
    private Long id;
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
    private LocalDateTime createdDateTime;

    @Builder
    public TourInformationResponse(TourInformation tourInformation) {
        this.id = tourInformation.getId();
        this.season = tourInformation.getSeason();
        this.regionClassification = tourInformation.getRegionClassification();
        this.travelStyle1 = tourInformation.getTourStyle().getTravelStyle1();
        this.travelStyle2 = tourInformation.getTourStyle().getTravelStyle2();
        this.travelStyle3 = tourInformation.getTourStyle().getTravelStyle3();
        this.travelStyle4 = tourInformation.getTourStyle().getTravelStyle4();
        this.travelStyle5 = tourInformation.getTourStyle().getTravelStyle5();
        this.travelStyle6 = tourInformation.getTourStyle().getTravelStyle6();
        this.travelStyle7 = tourInformation.getTourStyle().getTravelStyle7();
        this.travelStyle8 = tourInformation.getTourStyle().getTravelStyle8();
        this.createdDateTime = tourInformation.getCreatedDateTime();
    }
}
