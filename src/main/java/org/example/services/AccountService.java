package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.PasswordService;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.ConflictException;
import org.example.exceptions.errors.AccountError;
import org.example.mappers.AccountMapper;
import org.example.models.entities.Account;
import org.example.models.requests.AccountRequest;
import org.example.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Objects;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;
    private PasswordService passwordService;
    private Clock clock;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, PasswordService passwordService, Clock clock) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordService = passwordService;
        this.clock = clock;
    }

    /**
     * Create an account.
     *
     * @param accountRequest the account request
     * @return an account response
     */
    public Account createAccount(AccountRequest accountRequest) {
        log.debug("Create account with userName: {} ", accountRequest.getUserName());
        if (Objects.isNull(accountRequest.getUserName()) ||
                Objects.isNull(accountRequest.getPassword())) {
            throw new BadRequestException(AccountError.INVALID_REQUEST, "Username or Password are empty·");
        }

        String userName = accountRequest.getUserName().trim().toLowerCase();

        if (accountRepository.existsByUserName(userName)) {
            throw new ConflictException(AccountError.CONFLICT_USERNAME, userName);
        }

        String encryptedPassword = passwordService.encryptPassword(accountRequest.getPassword().trim());

        Account updatedAccount = accountMapper.mapToAccount(userName, encryptedPassword, OffsetDateTime.now(clock), OffsetDateTime.now(clock));

        return accountRepository.save(updatedAccount);
    }
}
