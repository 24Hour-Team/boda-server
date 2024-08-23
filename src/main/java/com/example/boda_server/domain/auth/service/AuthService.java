package com.example.boda_server.domain.auth.service;

import com.example.boda_server.domain.user.dto.UserPartialDto;
import com.example.boda_server.domain.user.entity.Role;
import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.repository.UserRepository;
import com.example.boda_server.global.constant.SessionConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(HttpServletRequest http, UserPartialDto userPartialDto){
        HttpSession session = http.getSession();
        String email = (String) session.getAttribute(SessionConstants.OAUTH_EMAIL);
        String profileUrl = (String) session.getAttribute(SessionConstants.OAUTH_PROFILE_URL);

        User user = User.builder()
                .email(email)
                .nickname(userPartialDto.getNickname())
                .gender(userPartialDto.getGender())
                .ageRange(userPartialDto.getAgeRange())
                .role(Role.USER)
                .profileImageUrl(profileUrl)
                .build();

        return userRepository.save(user);
    }
}
