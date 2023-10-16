package com.engineerLee.rolloverapi.user.repository;

import com.engineerLee.rolloverapi.user.models.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<AppUser, String> {
    List<AppUser> findByFirstNameStartsWith(String name);
    AppUser findByEmail(String email);
    AppUser findByPhoneNumber(String phone);

    Optional<AppUser> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
