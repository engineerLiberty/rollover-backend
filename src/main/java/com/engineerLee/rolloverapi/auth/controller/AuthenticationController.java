package com.engineerLee.rolloverapi.auth.controller;

import com.engineerLee.rolloverapi.auth.request.AuthenticationRequest;
import com.engineerLee.rolloverapi.auth.response.AuthenticationResponse;
import com.engineerLee.rolloverapi.auth.service.AuthenticationService;
import com.engineerLee.rolloverapi.auth.request.RegistrationRequest;
import com.engineerLee.rolloverapi.auth.response.RegistrationResponse;
import com.engineerLee.rolloverapi.security.JwtService;
import com.engineerLee.rolloverapi.token.model.Token;
import com.engineerLee.rolloverapi.token.request.RefreshTokenRequest;
import com.engineerLee.rolloverapi.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final JwtService jwtService;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authRequest) {
        return authenticationService.login(authRequest);
    }

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponse signUp(@RequestBody RegistrationRequest registrationRequest) {
        return authenticationService.registerNewUser(registrationRequest);
    }

    @PostMapping("refreshToken")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest tokenRequest) {
      return  tokenService.refreshToken(tokenRequest);
    }

//    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest tokenRequest) {
//      return  tokenService.findByToken(tokenRequest.getTokenId())
//                .map(tokenService::verifyExpirationDate)
//                .map(Token::getAppUser)
//                .map(appUser -> {
//                    String accessToken = jwtService.generateToken(appUser);
//                    return AuthenticationResponse.builder()
//                            .accessToke(accessToken)
//                            .refreshTokenId(tokenRequest.getTokenId())
//                            .build();
//                }).orElseThrow(()->new RuntimeException("refresh token not in database"));
//    }
}

