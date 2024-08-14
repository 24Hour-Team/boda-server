package com.example.boda_server.domain.recommendation.repository;

import com.example.boda_server.domain.recommendation.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}
