package com.engineerLee.rolloverapi.token.service;

import com.engineerLee.rolloverapi.auth.response.AuthenticationResponse;
import com.engineerLee.rolloverapi.security.JwtService;
import com.engineerLee.rolloverapi.token.model.Token;
import com.engineerLee.rolloverapi.token.repository.CustomTokenRepository;
import com.engineerLee.rolloverapi.token.repository.TokenRepository;
import com.engineerLee.rolloverapi.token.request.RefreshTokenRequest;
import com.engineerLee.rolloverapi.user.models.AppUser;
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
    private final CustomTokenRepository customTokenRepository;

    public Token createAuthenticationToken(String userName) {
        var user = usersRepository.findByUserName(userName).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokedAllValidToken(user);
        return tokenRepository.save(Token.builder()
                .tokenType(BEARER.name())
                .jwtToken(jwtToken)
                .refreshToken(UUID.randomUUID().toString())
                .appUser(usersRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not Found")))
                .expirationDate(Instant.now().plusMillis(600000)) //10 minutes
                .build());
    }

    public Optional<Token>findByToken(String token) {
        return tokenRepository.findByRefreshToken(token);
    }

    public Token verifyExpirationDate(Token token) {
        if (token.getExpirationDate().compareTo(Instant.now())<0){
            tokenRepository.delete(token); //User token can still be stored for future reference
            throw new RuntimeException("Refresh token has expired, please make new sign in request");
        }
        return token;
    }
    public AuthenticationResponse refreshToken(RefreshTokenRequest tokenRequest) {
      Token token = verifyExpirationDate(findByToken(tokenRequest.getTokenId())
              .orElseThrow(()->new RuntimeException("Token not registered in database")));
      String accessToken = createAuthenticationToken(token.getAppUser().getUsername()).getJwtToken();
      return AuthenticationResponse.builder()
              .accessToke(accessToken)
              .refreshTokenId(token.getRefreshToken())
              .build();
    }
    private void revokedAllValidToken(AppUser appUser) {
       var validUserToken = customTokenRepository.findByUserIdAndExpiredOrRevokedFalse(appUser.getId());
        if (validUserToken.isEmpty())
            return;
        validUserToken.forEach(t-> {
            t.setRevoked(true);
            t.setExpired(true);
            t.setAppUser(appUser);
        });
        tokenRepository.saveAll(validUserToken);
    }
}
