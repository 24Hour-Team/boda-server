package com.example.boda_server.domain.spot.repository;

import com.example.boda_server.domain.spot.entity.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    Optional<Spot> findByNameAndCityName(String name, String cityName);
    Page<Spot> findByNameContaining(String name, Pageable pageable);
}
