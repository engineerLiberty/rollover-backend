package com.engineerLee.rolloverapi.registration.repository;

import com.engineerLee.rolloverapi.registration.domain.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users, Long> {
}
