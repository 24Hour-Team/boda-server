package com.example.boda_server.domain.auth.controller;

import com.example.boda_server.domain.auth.service.AuthService;
import com.example.boda_server.domain.user.dto.UserPartialDto;
import com.example.boda_server.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(HttpServletRequest http, @RequestBody UserPartialDto userPartialDto){
        User created= authService.register(http, userPartialDto);

        return ResponseEntity.status(HttpStatus.OK).body(created);
    }
}
