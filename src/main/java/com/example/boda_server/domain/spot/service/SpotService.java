package com.example.boda_server.domain.spot.service;

import com.example.boda_server.domain.recommendation.exception.RecommendationErrorCode;
import com.example.boda_server.domain.recommendation.exception.RecommendationException;
import com.example.boda_server.domain.spot.dto.response.SpotResponse;
import com.example.boda_server.domain.spot.dto.response.SpotSearchResponse;
import com.example.boda_server.domain.spot.entity.Spot;
import com.example.boda_server.domain.spot.exception.SpotErrorCode;
import com.example.boda_server.domain.spot.exception.SpotException;
import com.example.boda_server.domain.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SpotService {

    private final SpotRepository spotRepository;

    /**
     * 여행지 상세 조회 로직
     */
    public SpotResponse getSpot(Long spotId) {
        log.info("Fetching spot details for spotId: {}", spotId);
        return SpotResponse.builder()
                .spot(spotRepository.findById(spotId).orElseThrow(
                        () -> new SpotException(SpotErrorCode.SPOT_NOT_FOUND)
                ))
                .build();
    }

    /**
     * 여행지 검색 로직
     */
    public Page<SpotSearchResponse> searchSpots(String name, Pageable pageable) {
        log.info("Searching spots with name containing '{}', page: {}, size: {}",
                name, pageable.getPageNumber(), pageable.getPageSize());
        Page<Spot> spots = spotRepository.findByNameContaining(name, pageable);
        return spots.map(spot -> SpotSearchResponse.builder()
                .spot(spot)
                .build());
    }

    // id로 여행지를 반환
    public Spot findSpotById(Long spotId) {
        return spotRepository.findById(spotId)
                .orElseThrow(() -> {
                    log.error("Spot not found with id: {}", spotId);
                    return new SpotException(SpotErrorCode.SPOT_NOT_FOUND);
                });
    }
}
