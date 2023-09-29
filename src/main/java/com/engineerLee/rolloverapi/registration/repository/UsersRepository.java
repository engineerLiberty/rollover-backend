package com.engineerLee.rolloverapi.registration.repository;

import com.engineerLee.rolloverapi.registration.domain.Users;
import com.engineerLee.rolloverapi.registration.request.RegistrationRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<Users, Long> {
    @Query("{phoneNumber:0}")
    Optional<Users> findByPhoneNumber(String phoneNumber);
}
