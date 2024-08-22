package com.example.boda_server.domain.spot.controller;

import com.example.boda_server.domain.spot.dto.response.SpotResponse;
import com.example.boda_server.domain.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spot")
public class SpotController {
    private final SpotService spotService;

    @GetMapping("/{spotId}")
    public ResponseEntity<SpotResponse> getSpot(
            @PathVariable("spotId") Long spotId
    ){
        return ResponseEntity.ok()
                .body(spotService.getSpot(spotId));
    }
}
