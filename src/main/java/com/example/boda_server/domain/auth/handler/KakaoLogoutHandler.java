package com.example.boda_server.domain.auth.handler;


import com.example.boda_server.domain.auth.exception.AuthErrorCode;
import com.example.boda_server.global.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KakaoLogoutHandler implements LogoutHandler{

    @Value("${kakao.api.key}")
    private String clientId;

    @Value("${logoutRedirectUri}")
    private String logoutRedirectUri;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 카카오 로그아웃 URL 생성
        String kakaoLogoutUrl = String.format(
                "https://kauth.kakao.com/oauth/logout?client_id=%s&logout_redirect_uri=%s",
                clientId, logoutRedirectUri);

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 시큐리티 컨텍스트 로그아웃 처리
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, authentication);

        // 카카오 로그아웃 URL로 리다이렉트
        try {
            response.sendRedirect(kakaoLogoutUrl);
        } catch (Exception e) {
            throw new BusinessException(AuthErrorCode.CANNOT_LOGOUT_OAUTH_SERVICE);
        }

    }

}

