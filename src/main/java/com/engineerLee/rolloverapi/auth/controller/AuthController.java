package com.engineerLee.rolloverapi.auth.controller;

import com.engineerLee.rolloverapi.auth.request.AuthRequest;
import com.engineerLee.rolloverapi.auth.response.AuthResponse;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import com.engineerLee.rolloverapi.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

private final RegistrationService registrationService;
    @PostMapping("login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponse login( @RequestBody AuthRequest authRequest) {
        return registrationService.login(authRequest);
    }

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse signUp(@RequestBody RegistrationRequest registrationRequest) {
        return registrationService.registerNewUser(registrationRequest);
    }
}
