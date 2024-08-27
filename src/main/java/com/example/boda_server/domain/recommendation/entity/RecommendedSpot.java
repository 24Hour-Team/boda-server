package com.example.boda_server.domain.recommendation.entity;

import com.example.boda_server.domain.spot.entity.Spot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedSpot {

    @Id
    @GeneratedValue
    @Column(name = "recommended_spot_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_information_id")
    private TourInformation tourInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @Builder
    public RecommendedSpot(TourInformation tourInformation, Spot spot) {
        this.tourInformation = tourInformation;
        this.spot = spot;
    }
}
