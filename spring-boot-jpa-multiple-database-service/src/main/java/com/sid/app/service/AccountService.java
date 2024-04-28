package com.sid.app.service;

import com.sid.app.entity.account.Account;
import com.sid.app.repository.account.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Siddhant Patni
 */
@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAccountList() {
        return accountRepository.findAll();
    }

}