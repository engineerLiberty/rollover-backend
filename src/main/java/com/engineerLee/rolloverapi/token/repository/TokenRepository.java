package com.engineerLee.rolloverapi.token.repository;

import com.engineerLee.rolloverapi.token.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token,String> {
//    Optional<Token> findByToken(String token);
    Optional<Token> findByJwtToken(String jwtToken);

    Optional<Token> findByRefreshToken(String token);
}
