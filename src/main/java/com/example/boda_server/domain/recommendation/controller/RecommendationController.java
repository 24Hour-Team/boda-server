package com.example.boda_server.domain.recommendation.controller;

import com.example.boda_server.domain.auth.dto.CustomOAuth2User;
import com.example.boda_server.domain.recommendation.dto.request.RecommendationRequest;
import com.example.boda_server.domain.recommendation.dto.response.RecommendationResponse;
import com.example.boda_server.domain.recommendation.dto.response.TourInformationResponse;
import com.example.boda_server.domain.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<Long> recommend(
            @RequestBody RecommendationRequest recommendationRequest,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        return ResponseEntity.ok()
                .body(recommendationService.recommend(recommendationRequest, customOAuth2User.getUser().getEmail()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<TourInformationResponse>> getTourInformations(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        return ResponseEntity.ok()
                .body(recommendationService.getTourInformations(customOAuth2User.getUser().getEmail()));
    }

    @GetMapping("/{tourInformationId}")
    public ResponseEntity<RecommendationResponse> getRecommendedSpots(
            @PathVariable("tourInformationId") Long tourInformationId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        return ResponseEntity.ok()
                .body(recommendationService.getRecommendedSpots(tourInformationId, customOAuth2User.getUser().getEmail()));
    }
}
