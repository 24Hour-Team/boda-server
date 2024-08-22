package com.example.boda_server.domain.recommendation.service;

import com.example.boda_server.domain.recommendation.dto.response.TourInformationResponse;
import com.example.boda_server.domain.recommendation.entity.*;
import com.example.boda_server.domain.recommendation.exception.RecommendationErrorCode;
import com.example.boda_server.domain.recommendation.exception.RecommendationException;
import com.example.boda_server.domain.recommendation.repository.TourInformationRepository;
import com.example.boda_server.domain.spot.dto.response.SpotResponse;
import com.example.boda_server.domain.spot.entity.Spot;
import com.example.boda_server.domain.user.entity.AgeRange;
import com.example.boda_server.domain.user.entity.Gender;
import com.example.boda_server.domain.user.entity.Role;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private TourInformationRepository tourInformationRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RecommendationService recommendationService;

    private User user;
    private Spot spot;
    private TourInformation tourInformation;
    private TourStyle tourStyle;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .role(Role.USER)
                .nickname("nickname")
                .gender(Gender.MALE)
                .profileImageUrl("profileImageUrl")
                .ageRange(AgeRange.TWENTIES)
                .email("test@example.com")
                .build();

        spot = Spot.builder()
                .id(1L)
                .name("성산일출봉")
                .xCoord("33.4592")
                .yCoord("126.9427")
                .address("제주특별자치도 서귀포시 성산읍")
                .cityName("서귀포시")
                .build();

        tourStyle = TourStyle.builder()
                .travelStyle1(true)
                .travelStyle2(false)
                .travelStyle3(true)
                .travelStyle4(false)
                .travelStyle5(true)
                .travelStyle6(false)
                .travelStyle7(true)
                .travelStyle8(false)
                .build();

        tourInformation = TourInformation.builder()
                .season(Season.SPRING)
                .regionClassification(RegionClassification.JEJU)
                .tourStyle(tourStyle)
                .user(user)
                .build();
    }

    @Test
    @DisplayName("지난 추천 리스트 조회 성공")
    void getTourInformations_Success() {
        // Arrange
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(tourInformationRepository.findByUserWithTourStyle(any(User.class)))
                .thenReturn(Collections.singletonList(tourInformation));

        // Act
        List<TourInformationResponse> response = recommendationService.getTourInformations("test@example.com");

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        verify(tourInformationRepository, times(1)).findByUserWithTourStyle(any(User.class));
    }

    @Test
    @DisplayName("지난 추천 결과 조회 성공")
    void getRecommendedSpots_Success() {
        // Arrange
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(tourInformationRepository.findById(anyLong())).thenReturn(Optional.of(tourInformation));

        RecommendedSpot recommendedSpot = RecommendedSpot.builder()
                .tourInformation(tourInformation)
                .spot(spot)
                .build();

        tourInformation.addRecommendedSpot(recommendedSpot);

        // Act
        List<SpotResponse> response = recommendationService.getRecommendedSpots(1L, "test@example.com");

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("성산일출봉", response.get(0).getName());
        verify(tourInformationRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("지난 추천 결과 조회 실패 - 권한 없는 접근 시도")
    void getRecommendedSpots_UnauthorizedAccess() {
        // Arrange
        when(userService.findUserByEmail(anyString())).thenReturn(user);

        User anotherUser = User.builder().email("another@example.com").build();
        TourInformation anotherTourInformation = TourInformation.builder().user(anotherUser).build();

        when(tourInformationRepository.findById(anyLong())).thenReturn(Optional.of(anotherTourInformation));

        // Act & Assert
        RecommendationException exception = assertThrows(RecommendationException.class, () ->
                recommendationService.getRecommendedSpots(1L, "test@example.com"));

        assertEquals(RecommendationErrorCode.UNAUTHORIZED_RECOMMENDATION_ACCESS, exception.getErrorCode());
        verify(tourInformationRepository, times(1)).findById(anyLong());
    }
}