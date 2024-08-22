package com.example.boda_server.domain.spot.service;

import com.example.boda_server.domain.spot.dto.response.SpotResponse;
import com.example.boda_server.domain.spot.dto.response.SpotSearchResponse;
import com.example.boda_server.domain.spot.entity.Spot;
import com.example.boda_server.domain.spot.exception.SpotErrorCode;
import com.example.boda_server.domain.spot.exception.SpotException;
import com.example.boda_server.domain.spot.repository.SpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpotServiceTest {

    @Mock
    private SpotRepository spotRepository;

    @InjectMocks
    private SpotService spotService;

    private Spot spot;

    @BeforeEach
    void setUp() {
        spot = Spot.builder()
                .id(1L)
                .name("성산일출봉")
                .xCoord("33.4592")
                .yCoord("126.9427")
                .address("제주특별자치도 서귀포시 성산읍")
                .cityName("서귀포시")
                .build();
    }

    @Test
    @DisplayName("여행지 상세 정보 조회 성공")
    void getSpot_Success() {
        when(spotRepository.findById(anyLong())).thenReturn(Optional.of(spot));

        SpotResponse response = spotService.getSpot(1L);

        assertNotNull(response);
        assertEquals(spot.getName(), response.getName());
        verify(spotRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("여행지 상세 정보 조회 실패 - 존재하지 않는 ID로 조회")
    void getSpot_NotFound() {
        when(spotRepository.findById(anyLong())).thenReturn(Optional.empty());

        SpotException exception = assertThrows(SpotException.class, () -> spotService.getSpot(1L));
        assertEquals(SpotErrorCode.SPOT_NOT_FOUND, exception.getErrorCode());
        verify(spotRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("여행지 검색 성공")
    void searchSpots_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Spot> spots = new PageImpl<>(Collections.singletonList(spot));
        when(spotRepository.findByNameContaining(anyString(), any(Pageable.class))).thenReturn(spots);

        Page<SpotSearchResponse> result = spotService.searchSpots("일출", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("성산일출봉", result.getContent().get(0).getName());
        verify(spotRepository, times(1)).findByNameContaining("일출", pageable);
    }
}