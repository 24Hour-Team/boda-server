package com.example.boda_server.domain.recommendation.service;

import com.example.boda_server.domain.recommendation.repository.RecommendedSpotRepository;
import com.example.boda_server.domain.recommendation.repository.SpotRepository;
import com.example.boda_server.domain.recommendation.repository.TourInformationRepository;
import com.example.boda_server.domain.recommendation.repository.TourStyleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecommendationService {

    private final RecommendedSpotRepository recommendedSpotRepository;
    private final SpotRepository spotRepository;
    private final TourInformationRepository tourInformationRepository;
    private final TourStyleRepository tourStyleRepository;


}
