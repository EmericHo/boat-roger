package org.example.repositories;

import org.example.models.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {

    /**
     * Find account by the userName.
     *
     * @param userName the userName.
     * @return an optional account
     */
    Optional<Account> findByUserName(String userName);

    /**
     * Return if userName already exists or not.
     *
     * @param userName the userName
     * @return a boolean
     */
    boolean existsByUserName(String userName);


}
