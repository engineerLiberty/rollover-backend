package com.engineerLee.rolloverapi.registration.service;

import com.engineerLee.rolloverapi.registration.domain.Users;
import com.engineerLee.rolloverapi.registration.repository.UsersRepository;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegistrationService {
    private final UsersRepository usersRepository;

    public RegistrationResponse registerNewUser(RegistrationRequest registrationRequest) {
        Users  users= Users.builder()
                .userName(registrationRequest.getUserName())
                .firstName(registrationRequest.getFirstName())
                .otherName(registrationRequest.getOtherName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .lastName(registrationRequest.getLastName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .email(registrationRequest.getEmail())
                .passWord(registrationRequest.getPassWord())
                .pin(registrationRequest.getPin())
                .dob(registrationRequest.getDob())
                .address(registrationRequest.getAddress())
                .state_of_origin(registrationRequest.getState_of_origin())
                .state_of_residence(registrationRequest.getState_of_residence())
                .nationality(registrationRequest.getNationality())
                .build();
        log.info(users);
        usersRepository.save(users);
        String name = users.getFirstName() + " " + users.getLastName();
        if (users.getOtherName() != null) {
            name = users.getFirstName() + " " + users.getOtherName() + " " + users.getLastName();
        }
        return RegistrationResponse.builder()
                .name(name)
                .userName(registrationRequest.getUserName())
                .build();
    }
}
