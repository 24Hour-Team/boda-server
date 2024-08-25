package com.example.boda_server.domain.auth.service;

import com.example.boda_server.domain.user.dto.request.UserPartialRequest;
import com.example.boda_server.domain.user.dto.response.UserResponse;
import com.example.boda_server.domain.user.entity.Role;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.repository.UserRepository;
import com.example.boda_server.global.constant.SessionConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    /**
     * 소셜로그인에서 받아온 정보와 사용자가 추가 입력한 정보를 저장하는 로직
     */
    public UserResponse register(HttpServletRequest http, UserPartialRequest userPartialRequest){
        HttpSession session = http.getSession();
        String email = (String) session.getAttribute(SessionConstants.OAUTH_EMAIL);
        String profileImageUrl = (String) session.getAttribute(SessionConstants.OAUTH_PROFILE_URL);

        User user = User.builder()
                .email(email)
                .nickname(userPartialRequest.getNickname())
                .gender(userPartialRequest.getGender())
                .ageRange(userPartialRequest.getAgeRange())
                .role(Role.USER)
                .profileImageUrl(profileImageUrl)
                .build();

        User created = userRepository.save(user);

        return UserResponse.builder()
                .user(created)
                .build();
    }
}
