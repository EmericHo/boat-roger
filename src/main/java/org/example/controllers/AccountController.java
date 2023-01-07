package org.example.controllers;

import org.example.models.entities.Account;
import org.example.models.requests.AccountRequest;
import org.example.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3001"}, methods = {POST})
@RequestMapping(path = "/account", produces = "application/json; charset=UTF-8")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }
}
