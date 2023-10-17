package com.engineerLee.rolloverapi.auth.service;

import com.engineerLee.rolloverapi.auth.request.AuthenticationRequest;
import com.engineerLee.rolloverapi.auth.request.RegistrationRequest;
import com.engineerLee.rolloverapi.auth.response.AuthenticationResponse;
import com.engineerLee.rolloverapi.auth.response.RegistrationResponse;
import com.engineerLee.rolloverapi.exceptions.InsufficientUserDetailsException;
import com.engineerLee.rolloverapi.security.JwtService;
import com.engineerLee.rolloverapi.token.model.Token;
import com.engineerLee.rolloverapi.token.repository.CustomTokenRepository;
import com.engineerLee.rolloverapi.token.repository.TokenRepository;
import com.engineerLee.rolloverapi.token.service.TokenService;
import com.engineerLee.rolloverapi.user.models.AppUser;
import com.engineerLee.rolloverapi.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.engineerLee.rolloverapi.enums.Roles.ROLE_USER;
import static com.engineerLee.rolloverapi.enums.Status.PENDING;
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    public RegistrationResponse registerNewUser(RegistrationRequest registrationRequest) {

        validateRegistrationRequest(registrationRequest);
        String name = registrationRequest.getFirstName() + " " + registrationRequest.getLastName();
        if (registrationRequest.getOtherName() != null && !registrationRequest.getOtherName().isEmpty()) {
            name = registrationRequest.getFirstName() + " " + registrationRequest.getOtherName() + " " + registrationRequest.getLastName();
        }
        AppUser newUser = AppUser.builder()
                .fullName(name)
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .otherName(registrationRequest.getOtherName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .userName(registrationRequest.getUserName())
                .email(registrationRequest.getEmail())
                .passWord(passwordEncoder.encode(registrationRequest.getPassWord()))
                .pin(registrationRequest.getPin())
                .dob(registrationRequest.getDob())
                .roles(ROLE_USER)
                .status(PENDING)
                .address(registrationRequest.getAddress())
                .state_of_origin(registrationRequest.getState_of_origin())
                .state_of_residence(registrationRequest.getState_of_residence())
                .nationality(registrationRequest.getNationality())
                .build();
        usersRepository.save(newUser);
        return RegistrationResponse.builder()
                .fullName(name)
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .port_whatsApp_link("www.whatsapp-test-port.com?not-a-valid-link")
                .group_whatApp_link("www.whatsapp-test-group.com?not-a-valid-link")
                .build();
    }
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(),
                authRequest.getPassWord()
        ));
//        var user = usersRepository.findByUserName(authRequest.getUserName()).orElseThrow();
        Token authenticationToken = tokenService.createAuthenticationToken(authRequest.getUserName());
//        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToke(authenticationToken.getJwtToken())
                .refreshTokenId(authenticationToken.getRefreshToken())
                .build();
    }


    private void validateRegistrationRequest(RegistrationRequest request) {
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new InsufficientUserDetailsException("Firstname must be provided");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new InsufficientUserDetailsException("Lastname not provided");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()) {
            throw new InsufficientUserDetailsException("Phone number not provided");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new InsufficientUserDetailsException("Email not provided");
        }
        if (request.getPassWord() == null || request.getPassWord().isEmpty()) {
            throw new InsufficientUserDetailsException("Password not provided");
        }
        if (request.getUserName() == null || request.getUserName().isEmpty()) {
            throw new InsufficientUserDetailsException("Username not provided");
        }
        if (request.getPin() == null || request.getPin().isEmpty()) {
            throw new InsufficientUserDetailsException("Secret payout pin not provided");
        }
        if (usersRepository.findByPhoneNumber(request.getPhoneNumber()) != null) {
            throw new RuntimeException("User Already exist with this phone number: " + request.getPhoneNumber());
        }
        if (usersRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already exist: " + request.getEmail());
        }
        if (usersRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new InsufficientUserDetailsException("User already exist with username");
        }
    }
}
