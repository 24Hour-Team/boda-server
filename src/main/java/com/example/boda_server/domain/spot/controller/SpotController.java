package com.example.boda_server.domain.spot.controller;

import com.example.boda_server.domain.spot.dto.response.SpotResponse;
import com.example.boda_server.domain.spot.dto.response.SpotSearchResponse;
import com.example.boda_server.domain.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<SpotSearchResponse>> searchSpots(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok()
                .body(spotService.searchSpots(name, PageRequest.of(page, size)));
    }
}
