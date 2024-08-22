package com.example.boda_server.domain.recommendation.repository;

import com.example.boda_server.domain.recommendation.entity.TourInformation;
import com.example.boda_server.domain.user.entity.User;

import java.util.List;

public interface TourInformationRepositoryCustom {
    List<TourInformation> findByUserWithTourStyle(User user);
}
