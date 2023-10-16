package com.engineerLee.rolloverapi.token.model;

import com.engineerLee.rolloverapi.user.models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    private String id;
    private String token;
    private String tokenType;
    private boolean revoked;
    private Instant expirationDate;
    @DBRef
    private AppUser appUser;
}
