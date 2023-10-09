package com.engineerLee.rolloverapi.registration.service;

import com.engineerLee.rolloverapi.registration.domain.User;
import com.engineerLee.rolloverapi.registration.repository.UsersRepository;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import com.engineerLee.rolloverapi.registration.response.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final MongoTemplate mongoTemplate;
    private  Pageable pageable;

    public RegistrationResponse registerNewUser(RegistrationRequest registrationRequest) {

        validateRegistrationRequest(registrationRequest);
        User newUser = User.builder()
                .userName(registrationRequest.getUserName())
                .firstName(registrationRequest.getFirstName())
                .otherName(registrationRequest.getOtherName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .passWord(registrationRequest.getPassWord())
                .pin(registrationRequest.getPin())
                .dob(registrationRequest.getDob())
                .address(registrationRequest.getAddress())
                .state_of_origin(registrationRequest.getState_of_origin())
                .state_of_residence(registrationRequest.getState_of_residence())
                .nationality(registrationRequest.getNationality())
                .build();
        usersRepository.save(newUser);
        String name = newUser.getFirstName() + " " + newUser.getLastName();
        if (newUser.getOtherName() != null) {
            name = newUser.getFirstName() + " " + newUser.getOtherName() + " " + newUser.getLastName();
        }
        return RegistrationResponse.builder()
                .fullName(name)
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .port_whatsApp_link("")
                .group_whatApp_link("")
                .build();
    }



    public List<User> getAllRegisteredUsers() {
       return usersRepository.findAll();
    }

    public List<User> getPersonStartWith(String name) {
        return usersRepository.findByFirstNameStartsWith(name);
    }

    public User getEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public User getPhone(String phone) {
        return usersRepository.findByPhoneNumber(phone);
    }
    private void validateRegistrationRequest(RegistrationRequest request) {
        if (usersRepository.findByPhoneNumber(request.getPhoneNumber()) != null){
            throw new RuntimeException("User Already exist with this phone number: "+request.getPhoneNumber());
        }
        if (usersRepository.findByEmail(request.getEmail())!=null){
            throw new RuntimeException("Email already exist: "+request.getEmail());
        }
    }

    public User findUserDetails(String email, String phoneNumber, String username) {

        User user;
        if (usersRepository.findByEmail(email) != null) {
            user = usersRepository.findByEmail(email);

        } else if (usersRepository.findByUserName(username) != null) {
            return usersRepository.findByUserName(username);

        } else if (usersRepository.findByPhoneNumber(phoneNumber) != null) {
            user = usersRepository.findByPhoneNumber(phoneNumber);

        } else {
            throw new RuntimeException("User does not exist on database");
        }

        return user;
    }

    public List<User> search(String firstName,String lastName,String email, String phoneNumber, String username) {

        Query query = new Query();

        List<Criteria> criteria = new ArrayList<>();
        if (firstName != null && !firstName.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(firstName,"i"));
        }
        if (lastName != null && !lastName.isEmpty()) {
            criteria.add(Criteria.where("lastName").regex(lastName,"i"));
        }
        if (email != null && !email.isEmpty()) {
            criteria.add(Criteria.where("email").regex(email,"i"));
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
        return mongoTemplate.find(query,User.class);

    }
}

