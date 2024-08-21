package com.example.boda_server.domain.auth.service;


import com.example.boda_server.domain.auth.dto.CustomOAuth2User;
import com.example.boda_server.domain.auth.dto.OAuth2Response;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 유저 정보(attributes) 가져오기
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        // 2. resistrationId 가져오기 (third-party id)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 3. userNameAttributeName 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 4. 유저 정보 dto 생성
        OAuth2Response oAuth2Response = OAuth2Response.of(registrationId, oAuth2UserAttributes);

        // 5. 회원가입 및 로그인
        User user = getOrSave(oAuth2Response);

        // 6. OAuth2User로 반환
        return new CustomOAuth2User(user, oAuth2UserAttributes, userNameAttributeName);
    }

    private User getOrSave(OAuth2Response oAuth2Response) {
        User user = userRepository.findByEmail(oAuth2Response.getEmail())
                .orElseGet(oAuth2Response::toEntity);
        return userRepository.save(user);
    }

}
