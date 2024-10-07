package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    // Leverage Constructor based Dependancy Injection
    @Autowired
    public AccountService (AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account newAccount){
        // UserName is blank or password is at least 4 char long
        if ((newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4) || accountRepository.findByUsername(newAccount.getUsername()).isPresent()){
            return null;
        }
        return accountRepository.save(newAccount);
    }

    public Account login(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()).orElse(null);
    }
}
