package com.example.boda_server.domain.recommendation.controller;

import com.example.boda_server.domain.recommendation.dto.request.RecommendationRequest;
import com.example.boda_server.domain.recommendation.dto.response.RecommendationResponse;
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
    public ResponseEntity<List<RecommendationResponse>> recommend(
            @RequestBody RecommendationRequest recommendationRequest,
            @PathVariable("email") String email
    ){
        return ResponseEntity.ok()
                .body(recommendationService.recommend(recommendationRequest, email));
    }
}
