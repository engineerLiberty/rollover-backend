package com.engineerLee.rolloverapi.token.service;

import com.engineerLee.rolloverapi.auth.response.AuthenticationResponse;
import com.engineerLee.rolloverapi.security.JwtService;
import com.engineerLee.rolloverapi.token.model.Token;
import com.engineerLee.rolloverapi.token.repository.TokenRepository;
import com.engineerLee.rolloverapi.token.request.RefreshTokenRequest;
import com.engineerLee.rolloverapi.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.engineerLee.rolloverapi.token.tokenType.TokenType.BEARER;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;

    public Token createRefreshToken(String userName) {
        return tokenRepository.save(Token.builder()
                .tokenType(BEARER.name())
                .token(UUID.randomUUID().toString())
                .revoked(false)
                .appUser(usersRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not Found")))
                .expirationDate(Instant.now().plusMillis(600000)) //10 minutes
                .build());
    }

    public Optional<Token>findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Token verifyExpirationDate(Token token) {
        if (token.getExpirationDate().compareTo(Instant.now())<0){
            tokenRepository.delete(token);
            throw new RuntimeException("Refresh token has expired, please make new sign in request");
        }
        return token;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest tokenRequest) {
      Token token = verifyExpirationDate(findByToken(tokenRequest.getTokenId())
              .orElseThrow(()->new RuntimeException("Token not registered in database")));
      String accessToken = jwtService.generateToken(token.getAppUser());
      return AuthenticationResponse.builder()
              .accessToke(accessToken)
              .refreshTokenId(token.getToken())
              .build();
    }
}
