package com.example.boda_server.domain.recommendation.repository;

import com.example.boda_server.domain.recommendation.entity.TourInformation;
import com.example.boda_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourInformationRepository extends JpaRepository<TourInformation, Long> {
    List<TourInformation> findByUserOrderByCreatedDateTimeAsc(User user);
}
