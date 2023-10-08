package com.engineerLee.rolloverapi.registration.repository;

import com.engineerLee.rolloverapi.registration.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends MongoRepository<User, String> {
    List<User> findByFirstNameStartsWith(String name);
    User findByEmail(String email);
    User findByPhoneNumber(String phone);

    User findByUserName(String username);
}
