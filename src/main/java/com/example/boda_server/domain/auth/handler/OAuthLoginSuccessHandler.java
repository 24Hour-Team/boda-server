package com.example.boda_server.domain.auth.handler;


import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.repository.UserRepository;
import com.example.boda_server.global.constant.SessionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> account = oAuth2User.getAttribute("kakao_account");
        String email = (String) account.get("email");
        log.info("현재 유저의 email: {}", email);

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        String profileImageUrl = (String) profile.get("profile_image_url");

        if (needsAdditionalInfo(email)) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstants.OAUTH_EMAIL, email);
            session.setAttribute(SessionConstants.OAUTH_PROFILE_URL, profileImageUrl);
            getRedirectStrategy().sendRedirect(request, response, frontendUrl+"/register");
        } else {
            getRedirectStrategy().sendRedirect(request, response, frontendUrl+"/");
        }
    }

        /**
         * 사용자의 추가 정보가 필요한지 확인하는 로직
         */
        private boolean needsAdditionalInfo(String email) {
            User target = userRepository.findByEmail(email).orElse(null);
            if (target == null){
                return true;
            }
            log.info("이미 저장된 유저의 email: {}", target.getEmail());
            return false;
        }
}
