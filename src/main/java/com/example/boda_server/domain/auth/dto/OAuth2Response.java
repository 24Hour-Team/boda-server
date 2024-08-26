package com.example.boda_server.domain.auth.dto;

import com.example.boda_server.domain.user.entity.Role;
import com.example.boda_server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class OAuth2Response {
    private String name;
    private String email;
    private String profileUrl;

    public OAuth2Response(String name, String email, String profileUrl) {
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public static OAuth2Response of(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKakao(attributes);
        } else {
            //throw new AuthException(ILLEGAL_REGISTRATION_ID);
            return null;
        }
    }

    private static OAuth2Response ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2Response.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profileUrl((String) profile.get("profile_image_url"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(name)
                .email(email)
                .profileImageUrl(profileUrl)
                .role(Role.USER)
                .build();
    }
}
