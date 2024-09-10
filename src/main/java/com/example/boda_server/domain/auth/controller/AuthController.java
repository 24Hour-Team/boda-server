package com.example.boda_server.domain.auth.controller;

import com.example.boda_server.domain.auth.service.AuthService;
import com.example.boda_server.domain.user.dto.response.UserResponse;
import com.example.boda_server.domain.user.dto.request.UserPartialRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @CrossOrigin(origins="http://boda-travel.kro.kr", maxAge=3600)
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(HttpServletRequest http, @RequestBody UserPartialRequest userPartialRequest){
        UserResponse created = authService.register(http, userPartialRequest);
        return ResponseEntity.ok().body(created);
    }
}
