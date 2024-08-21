package com.example.boda_server.domain.recommendation.entity;

import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.global.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TourInformation extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "tour_information_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Season season;

    @Enumerated(EnumType.STRING)
    private RegionClassification regionClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_style_id")
    private TourStyle tourStyle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //여행 정보가 삭제되면 추천 여행지도 삭제되도록
    @OneToMany(mappedBy = "tourInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendedSpot> recommendedSpots = new ArrayList<>();

    @Builder
    public TourInformation(Season season, RegionClassification regionClassification, TourStyle tourStyle, User user) {
        this.season = season;
        this.regionClassification = regionClassification;
        this.tourStyle = tourStyle;
        this.user = user;
    }

    public RecommendedSpot addRecommendedSpot(RecommendedSpot recommendedSpot) {
        recommendedSpots.add(recommendedSpot);
        return recommendedSpot;
    }

    public void removeRecommendedSpot(RecommendedSpot recommendedSpot) {
        recommendedSpots.remove(recommendedSpot);
    }
}
