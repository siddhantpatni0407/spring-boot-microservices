package com.sid.app.controller;

import com.sid.app.constants.AppConstants;
import com.sid.app.entity.account.Account;
import com.sid.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = AppConstants.SAVE_ACCOUNT_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Account saveAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }


    @GetMapping(value = AppConstants.GET_ACCOUNTS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAccountList() {
        return accountService.getAccountList();

    }

}