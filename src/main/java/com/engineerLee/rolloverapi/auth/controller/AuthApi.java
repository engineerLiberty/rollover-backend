package com.engineerLee.rolloverapi.auth.controller;

import com.engineerLee.rolloverapi.auth.request.AuthRequest;
import com.engineerLee.rolloverapi.auth.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthApi {

    @PostMapping("login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponse login(AuthRequest authRequest) {
        return null;
    }
}
