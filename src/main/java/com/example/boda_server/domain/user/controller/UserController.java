package com.example.boda_server.domain.user.controller;

import com.example.boda_server.domain.auth.dto.CustomOAuth2User;
import com.example.boda_server.domain.user.dto.response.UserResponse;
import com.example.boda_server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser( @AuthenticationPrincipal CustomOAuth2User customOAuth2User){

        return ResponseEntity.ok()
                .body(userService.getUser(customOAuth2User.getUser().getEmail()));
    }
}
