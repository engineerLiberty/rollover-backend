package com.engineerLee.rolloverapi.registration.repository;

import com.engineerLee.rolloverapi.registration.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
    List<User> findByFirstNameStartsWith(String name);
    User findByEmail(String email);
    User findByPhoneNumber(String phone);

    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
