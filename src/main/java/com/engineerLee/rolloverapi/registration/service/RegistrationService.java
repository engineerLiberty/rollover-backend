package com.engineerLee.rolloverapi.registration.service;

import com.engineerLee.rolloverapi.registration.domain.Users;
import com.engineerLee.rolloverapi.registration.repository.UsersRepository;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UsersRepository usersRepository;

    public RegistrationResponse registerNewUser(RegistrationRequest registrationRequest) {
        Users users = Users.builder()
                .email(registrationRequest.getEmail())
                .build();
         usersRepository.save(users);
         return RegistrationResponse.builder()
                 .userName(registrationRequest.getUserName())
                 .build();
    }

}
