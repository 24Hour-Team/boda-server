package com.example.boda_server.domain.recommendation.repository;

import com.example.boda_server.domain.recommendation.entity.RecommendedSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedSpotRepository extends JpaRepository<RecommendedSpot, Long> {
}
