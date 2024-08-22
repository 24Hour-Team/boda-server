package com.example.boda_server.domain.recommendation.repository;

import com.example.boda_server.domain.recommendation.entity.TourStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourStyleRepository extends JpaRepository<TourStyle, Long> {
    Optional<TourStyle> findByTravelStyle1AndTravelStyle2AndTravelStyle3AndTravelStyle4AndTravelStyle5AndTravelStyle6AndTravelStyle7AndTravelStyle8(
            Boolean travelStyle1,
            Boolean travelStyle2,
            Boolean travelStyle3,
            Boolean travelStyle4,
            Boolean travelStyle5,
            Boolean travelStyle6,
            Boolean travelStyle7,
            Boolean travelStyle8
    );
}
