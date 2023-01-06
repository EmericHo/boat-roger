package org.example.services;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.PasswordService;
import org.example.exceptions.NotFoundException;
import org.example.exceptions.UnauthorizedException;
import org.example.exceptions.errors.AccountError;
import org.example.exceptions.errors.AuthenticationError;
import org.example.managers.AuthenticationToken;
import org.example.managers.TokenManager;
import org.example.models.entities.Account;
import org.example.models.responses.AuthenticationResponse;
import org.example.repositories.AccountRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuthenticationService {

    private static final String BEARER = "bearer";
    private static final String CLAIM_SESSION_RANDOM = "sessionRandom";

    private AccountRepository accountRepository;
    private PasswordService passwordService;
    private TokenManager tokenManager;

    public AuthenticationService(AccountRepository accountRepository, PasswordService passwordService,
                                 TokenManager tokenManager) {
        this.accountRepository = accountRepository;
        this.passwordService = passwordService;
        this.tokenManager = tokenManager;
    }

    /**
     * Authenticate a user with password.
     *
     * @param userName
     * @param password
     * @return
     */
    public AuthenticationResponse withPassword(String userName, String password) {
        log.debug("Authentication with username: {}", userName);

        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException(AccountError.INVALID_REQUEST, userName));

        boolean isMatching = passwordService.passwordsMatch(password, account.getPassword());

        if (!isMatching) {
            throw new UnauthorizedException(AuthenticationError.INVALID_CREDENTIALS);
        }

        return AuthenticationResponse.builder()
                .accessToken(tokenManager.generateJWT(account))
                .tokenType(BEARER)
                .expiresIn(tokenManager.getJwtProperties().getTimeToLive())
                .build();
    }

    /**
     * Authenticate a user in Spring security using a JWT token.
     *
     * @param claims the claims of the JWT token
     * @param token  the original token
     * @return a Spring Authentication
     * @throws AuthenticationException if the user cannot be authenticated
     */
    public Authentication withToken(Claims claims, String token) throws AuthenticationException {
        log.trace("Looking up for a user with subject: {}", claims.getSubject());

        Account account = accountRepository.findById(claims.getSubject())
                .orElseThrow(() -> new BadCredentialsException("Unknown user <" + claims.getSubject() + ">"));

        log.debug("User: {} successfully authenticated", claims.getSubject());

        String sessionRandom = claims.get(CLAIM_SESSION_RANDOM, String.class);
        return new AuthenticationToken(account, List.of(), sessionRandom, token);
    }

}
