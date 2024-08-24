package com.example.boda_server.domain.recommendation.service;

import com.example.boda_server.domain.recommendation.dto.request.AIRecommendRequest;
import com.example.boda_server.domain.recommendation.dto.request.RecommendationRequest;
import com.example.boda_server.domain.recommendation.dto.response.RecommendationResponse;
import com.example.boda_server.domain.spot.dto.response.SpotResponse;
import com.example.boda_server.domain.recommendation.dto.response.TourInformationResponse;
import com.example.boda_server.domain.recommendation.entity.RecommendedSpot;
import com.example.boda_server.domain.spot.entity.Spot;
import com.example.boda_server.domain.recommendation.entity.TourInformation;
import com.example.boda_server.domain.recommendation.entity.TourStyle;
import com.example.boda_server.domain.recommendation.exception.RecommendationErrorCode;
import com.example.boda_server.domain.recommendation.exception.RecommendationException;
import com.example.boda_server.domain.recommendation.repository.RecommendedSpotRepository;
import com.example.boda_server.domain.spot.exception.SpotErrorCode;
import com.example.boda_server.domain.spot.exception.SpotException;
import com.example.boda_server.domain.spot.repository.SpotRepository;
import com.example.boda_server.domain.recommendation.repository.TourInformationRepository;
import com.example.boda_server.domain.recommendation.repository.TourStyleRepository;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecommendationService {

    private final RecommendedSpotRepository recommendedSpotRepository;
    private final SpotRepository spotRepository;
    private final TourInformationRepository tourInformationRepository;
    private final TourStyleRepository tourStyleRepository;
    private final UserService userService;

    @Value("${ai.url}")
    private String aiUrl;

    private static final int TRAVEL_STYLE_TRUE_VALUE = 7;
    private static final int TRAVEL_STYLE_FALSE_VALUE = 1;

    /**
     * 추천 로직 - AI 서버에 요청 후 응답 추천 결과는 저장(유저당 10개 제한)
     */
    public Long recommend(RecommendationRequest request, String email) {
        log.info("Starting recommendation process for user: {}", email);
        User user = userService.findUserByEmail(email);

        AIRecommendRequest aiRecommendRequest = buildAIRecommendRequest(request, user);  // ai 요청 dto 생성
        List<String> areas = fetchRecommendationsFromAI(aiRecommendRequest);  // ai 서버로 요청 처리

        TourStyle tourStyle = findOrCreateTourStyle(request);  // 여행 취향 엔티티가 존재하면 반환 없으면 생성해서 반환

        ensureMaxTenTourInformationForUser(user);  // 유저의 여행정보가 10개를 초과하는지 확인하고, 초과하면 가장 오래된 항목을 삭제

        // 여행 정보 저장
        TourInformation tourInformation = tourInformationRepository.save(TourInformation.builder()
                .tourStyle(tourStyle)
                .regionClassification(request.getRegionClassification())
                .season(request.getSeason())
                .user(user)
                .build());

        // ai 응답으로 여행지 가져오기
        List<Spot> spots = areas.stream()
                .map(area -> spotRepository.findByNameAndCityName(area, request.getRegionClassification().getLabel())
                        .orElseThrow(() -> new SpotException(SpotErrorCode.SPOT_NOT_FOUND)))
                .toList();

        // 추천 여행지 결과 저장
        spots.forEach(spot -> {
            RecommendedSpot recommendedSpot = RecommendedSpot.builder()
                    .tourInformation(tourInformation)
                    .spot(spot)
                    .build();
            recommendedSpotRepository.save(recommendedSpot);
            tourInformation.addRecommendedSpot(recommendedSpot);
        });

        return tourInformation.getId();
    }

    /**
     * 지난 추천 리스트 조회 로직
     */
    public List<TourInformationResponse> getTourInformations(String email) {
        log.info("Fetching tour information list for user: {}", email);
        User user = userService.findUserByEmail(email);

        return tourInformationRepository.findByUserWithTourStyle(user).stream().map(
                tourInformation -> TourInformationResponse.builder()
                        .tourInformation(tourInformation)
                        .build()
                ).toList();
    }

    /**
     * 지난 추천 결과 조회 로직
     */
    public RecommendationResponse getRecommendedSpots(Long tourInformationId, String email) {
        log.info("Fetching recommended spots for tourInformationId: {} and user: {}", tourInformationId, email);
        User user = userService.findUserByEmail(email);

        TourInformation tourInformation = findTourInformationById(tourInformationId);

        validateUserAccess(tourInformation, user);  // 사용자 접근 권한 검증

        return RecommendationResponse.builder()
                .tourInformation(tourInformation)
                .build();
    }

    // ai 요청 dto 생성
    private AIRecommendRequest buildAIRecommendRequest(RecommendationRequest request, User user) {
        return AIRecommendRequest.builder()
                .ageGrp(user.getAgeRange().getAgeNum())
                .cityNm(request.getRegionClassification().getLabel())
                .gender(user.getGender().getGenderNum())
                .season(request.getSeason().getSeasonNum())
                .travelStyl1(convertTravelStyle(request.getTravelStyle1()))
                .travelStyl2(convertTravelStyle(request.getTravelStyle2()))
                .travelStyl3(convertTravelStyle(request.getTravelStyle3()))
                .travelStyl4(convertTravelStyle(request.getTravelStyle4()))
                .travelStyl5(convertTravelStyle(request.getTravelStyle5()))
                .travelStyl6(convertTravelStyle(request.getTravelStyle6()))
                .travelStyl7(convertTravelStyle(request.getTravelStyle7()))
                .travelStyl8(convertTravelStyle(request.getTravelStyle8()))
                .build();
    }

    // 여행 취향 질문을 정수로 변환
    private int convertTravelStyle(Boolean travelStyle) {
        return Boolean.TRUE.equals(travelStyle) ? TRAVEL_STYLE_TRUE_VALUE : TRAVEL_STYLE_FALSE_VALUE;
    }

    // ai 서버로 요청 처리
    private List<String> fetchRecommendationsFromAI(AIRecommendRequest aiRecommendRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AIRecommendRequest> requestEntity = new HttpEntity<>(aiRecommendRequest, headers);

        try {
            ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
                    aiUrl + "/model",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );

            return Objects.requireNonNull(responseEntity.getBody())
                    .stream()
                    .map(map -> (String) map.get("AREA"))
                    .toList();
        } catch (Exception e) {
            log.error("Failed to fetch recommendations from AI server", e);
            throw new RecommendationException(RecommendationErrorCode.AI_SERVER_EXCEPTION);
        }
    }

    // 여행 취향 엔티티가 존재하면 반환 없으면 생성해서 반환
    private TourStyle findOrCreateTourStyle(RecommendationRequest request) {
        Optional<TourStyle> optionalTourStyle = tourStyleRepository.findByTravelStyle1AndTravelStyle2AndTravelStyle3AndTravelStyle4AndTravelStyle5AndTravelStyle6AndTravelStyle7AndTravelStyle8(
                request.getTravelStyle1(),
                request.getTravelStyle2(),
                request.getTravelStyle3(),
                request.getTravelStyle4(),
                request.getTravelStyle5(),
                request.getTravelStyle6(),
                request.getTravelStyle7(),
                request.getTravelStyle8()
        );

        // 엔티티가 존재하면 반환, 없으면 새로 생성하여 반환
        return optionalTourStyle.orElseGet(() -> {
            TourStyle newTourStyle = TourStyle.builder()
                    .travelStyle1(request.getTravelStyle1())
                    .travelStyle2(request.getTravelStyle2())
                    .travelStyle3(request.getTravelStyle3())
                    .travelStyle4(request.getTravelStyle4())
                    .travelStyle5(request.getTravelStyle5())
                    .travelStyle6(request.getTravelStyle6())
                    .travelStyle7(request.getTravelStyle7())
                    .travelStyle8(request.getTravelStyle8())
                    .build();
            return tourStyleRepository.save(newTourStyle);
        });
    }

     // 유저의 여행정보가 10개를 초과하는지 확인하고, 초과하면 가장 오래된 항목을 삭제
    private void ensureMaxTenTourInformationForUser(User user) {
        List<TourInformation> tourInformations = tourInformationRepository.findByUserOrderByCreatedDateTimeAsc(user);

        if (tourInformations.size() >= 10) {
            int excessCount = tourInformations.size() - 9; // 새로 추가될 TourInformation을 위해 하나를 남겨둠
            List<TourInformation> toDelete = tourInformations.subList(0, excessCount);

            log.info("Deleting {} oldest TourInformation entries for user: {}", excessCount, user.getEmail());
            tourInformationRepository.deleteAll(toDelete);  // TourInformation만 삭제하면 RecommendedSpot도 자동 삭제
        }
    }

    // id로 여행 정보를 반환
    private TourInformation findTourInformationById(Long tourInformationId) {
        return tourInformationRepository.findById(tourInformationId)
                .orElseThrow(() -> {
                    log.error("TourInformation not found with id: {}", tourInformationId);
                    return new RecommendationException(RecommendationErrorCode.TOUR_INFORMATION_NOT_FOUND);
                });
    }

    // 사용자 접근 권한 검증
    private void validateUserAccess(TourInformation tourInformation, User user) {
        if(!tourInformation.getUser().equals(user)) {
            log.warn("User: {} attempted to access tourInformation: {} which does not belong to them", user.getEmail(), tourInformation.getId());
            throw new RecommendationException(RecommendationErrorCode.UNAUTHORIZED_RECOMMENDATION_ACCESS);
        }
    }
}
