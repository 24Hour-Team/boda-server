package com.example.boda_server.domain.user.service;

import com.example.boda_server.domain.user.entity.User;
import com.example.boda_server.domain.user.exception.UserErrorCode;
import com.example.boda_server.domain.user.exception.UserException;
import com.example.boda_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;

    // email로 유저를 반환
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found: {}", email);
                    return new UserException(UserErrorCode.USER_NOT_FOUND);
                });
    }
}
