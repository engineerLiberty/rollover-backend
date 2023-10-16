package com.engineerLee.rolloverapi.user.service;

import com.engineerLee.rolloverapi.user.models.AppUser;
import com.engineerLee.rolloverapi.user.repository.UsersRepository;
import com.engineerLee.rolloverapi.user.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final MongoTemplate mongoTemplate;
    private final UsersRepository usersRepository;


    public AppUser findUserDetails(String email, String phoneNumber, String username) {

        AppUser user;
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

    public List<SearchResponse> search(String firstName, String lastName, String email, String phoneNumber, String username) {

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

        return mapUserToSearchResponse(mongoTemplate.find(query, AppUser.class));
    }

    public List<AppUser> getAllRegisteredUsers() {
        return usersRepository.findAll();
    }
    private List<SearchResponse> mapUserToSearchResponse(List<AppUser> listOfUser) {
        List<SearchResponse> searchResponseList = new ArrayList<>();
        for (AppUser user : listOfUser) {
            searchResponseList.add(
                    SearchResponse.builder()
                            .fullName(user.getFullName())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .otherName(user.getOtherName())
                            .userName(user.getUsername())
                            .roles(user.getRoles())
                            .status(user.getStatus())
                            .dob(user.getDob())
                            .address(user.getAddress())
                            .nationality(user.getNationality())
                            .state_of_residence(user.getState_of_residence())
                            .state_of_origin(user.getState_of_origin())
                            .email(user.getEmail())
                            .phoneNumber(user.getPhoneNumber())
                            .build()
            );
        }
        return searchResponseList;
    }

}

