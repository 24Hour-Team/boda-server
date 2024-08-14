package com.example.boda_server.domain.recommendation.repository;

import com.example.boda_server.domain.recommendation.entity.TourInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourInformationRepository extends JpaRepository<TourInformation, Long> {
}
