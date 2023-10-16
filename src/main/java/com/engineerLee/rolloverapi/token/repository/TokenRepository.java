package com.engineerLee.rolloverapi.token.repository;

import com.engineerLee.rolloverapi.token.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token,String> {
    Optional<Token> findByToken(String token);
}
