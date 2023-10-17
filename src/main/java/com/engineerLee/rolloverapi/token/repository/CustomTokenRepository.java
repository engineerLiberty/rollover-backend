package com.engineerLee.rolloverapi.token.repository;

import com.engineerLee.rolloverapi.token.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomTokenRepository {

    private final MongoTemplate mongoTemplate;

    public List<Token> findByUserIdAndExpiredOrRevokedFalse(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("appUser.id").is(userId).andOperator(Criteria.where("expired")
                        .is(false),
                Criteria.where("revoked").is(false)));
        return mongoTemplate.find(query, Token.class);
    }
}

