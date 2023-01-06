package org.example.mappers;

import org.example.models.entities.Account;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AccountMapper {

    /**
     * Map param in Account.
     *
     * @param userName  the username
     * @param password  the encrypted password
     * @param createdAt the creation date
     * @param updatedAt the update date
     * @return an account
     */
    public Account mapToAccount(String userName, String password, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return Account.builder()
                .userName(userName)
                .password(password)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
