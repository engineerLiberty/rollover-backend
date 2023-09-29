package com.engineerLee.rolloverapi.registration.controller;

import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import com.engineerLee.rolloverapi.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final RegistrationService registrationService;
    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse registration( @RequestBody RegistrationRequest registrationRequest) {
        return registrationService.registerNewUser(registrationRequest);
    }
}
