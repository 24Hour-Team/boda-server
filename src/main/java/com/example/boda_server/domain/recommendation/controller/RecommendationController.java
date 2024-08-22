package com.example.boda_server.domain.recommendation.controller;

import com.example.boda_server.domain.recommendation.dto.request.RecommendationRequest;
import com.example.boda_server.domain.recommendation.dto.response.SpotResponse;
import com.example.boda_server.domain.recommendation.dto.response.TourInformationResponse;
import com.example.boda_server.domain.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/{email}")
    public ResponseEntity<List<SpotResponse>> recommend(
            @RequestBody RecommendationRequest recommendationRequest,
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(recommendationService.recommend(recommendationRequest, email));
    }

    @GetMapping("/list/{email}")
    public ResponseEntity<List<TourInformationResponse>> getTourInformations(
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(recommendationService.getTourInformations(email));
    }

    @GetMapping("/{tourInformationId}/{email}")
    public ResponseEntity<List<SpotResponse>> getRecommendedSpots(
            @PathVariable("tourInformationId") Long tourInformationId,
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(recommendationService.getRecommendedSpots(tourInformationId, email));
    }

    @GetMapping("/spot/{spotId}")
    public ResponseEntity<SpotResponse> getSpot(
            @PathVariable("spotId") Long spotId
    ){
        return ResponseEntity.ok()
                .body(recommendationService.getSpot(spotId));
    }
}
