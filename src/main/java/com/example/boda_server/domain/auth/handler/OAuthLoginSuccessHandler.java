package com.example.boda_server.domain.auth.handler;


import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.repository.UserRepository;
import com.example.boda_server.global.constant.SessionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> account = oAuth2User.getAttribute("kakao_account");
        String email = (String) account.get("email");

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        String profileUrl = (String) profile.get("profile_url");

        if (needsAdditionalInfo(email)) {
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstants.OAUTH_EMAIL, email);
            session.setAttribute(SessionConstants.OAUTH_PROFILE_URL, profileUrl);
            getRedirectStrategy().sendRedirect(request, response, "/register");
        } else {
            getRedirectStrategy().sendRedirect(request, response, "/");
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
            return false;
        }
}
