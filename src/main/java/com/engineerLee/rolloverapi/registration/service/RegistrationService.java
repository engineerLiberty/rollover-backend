package com.engineerLee.rolloverapi.registration.service;

import com.engineerLee.rolloverapi.auth.request.AuthRequest;
import com.engineerLee.rolloverapi.auth.response.AuthResponse;
import com.engineerLee.rolloverapi.config.JwtService;
import com.engineerLee.rolloverapi.exceptions.InsufficientUserDetailsException;
import com.engineerLee.rolloverapi.exceptions.UserNotFoundException;
import com.engineerLee.rolloverapi.registration.models.User;
import com.engineerLee.rolloverapi.registration.repository.UsersRepository;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.engineerLee.rolloverapi.enums.Roles.ROLE_USER;
import static com.engineerLee.rolloverapi.enums.Status.PENDING;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public RegistrationResponse registerNewUser(RegistrationRequest registrationRequest) {

        validateRegistrationRequest(registrationRequest);
        String name = registrationRequest.getFirstName() + " " + registrationRequest.getLastName();
        if (registrationRequest.getOtherName() != null && !registrationRequest.getOtherName().isEmpty()) {
            name = registrationRequest.getFirstName() + " " + registrationRequest.getOtherName() + " " + registrationRequest.getLastName();
        }
        User newUser = User.builder()
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

public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(),
                authRequest.getPassWord()
        ));
        var user = usersRepository.findByUserName(authRequest.getUserName()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
}


    public User findUserDetails(String email, String phoneNumber, String username) {

        User user;
        if (usersRepository.findByEmail(email) != null) {
            user = usersRepository.findByEmail(email);

        } else if (usersRepository.findByUserName(username).isPresent()) {
            user = usersRepository.findByUserName(username).get();

        } else if (usersRepository.findByPhoneNumber(phoneNumber) != null) {
            user = usersRepository.findByPhoneNumber(phoneNumber);

        } else {
            throw new RuntimeException("User does not exist on this platform");
        }
        return user;
    }

    public List<User> search(String firstName, String lastName, String email, String phoneNumber, String username) {

        Query query = new Query();

        List<Criteria> criteria = new ArrayList<>();
        if (firstName != null && !firstName.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(firstName, "i"));
        }
        if (lastName != null && !lastName.isEmpty()) {
            criteria.add(Criteria.where("lastName").regex(lastName, "i"));
        }
        if (email != null && !email.isEmpty()) {
            criteria.add(Criteria.where("email").regex(email, "i"));
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            criteria.add(Criteria.where("phoneNumber").regex(phoneNumber));
        }
        if (username != null && !username.isEmpty()) {
            criteria.add(Criteria.where("username").regex(username));
        }
        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }
        return mongoTemplate.find(query, User.class);
    }

    public List<User> getAllRegisteredUsers() {

        return usersRepository.findAll();
    }
}

