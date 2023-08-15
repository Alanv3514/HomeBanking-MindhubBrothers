package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.models.Dtos.AccountDto;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @RequestMapping("/accounts")
    public List<AccountDto> getAll(){
        return accountRepository.findAll().stream()
                .map(account -> new AccountDto(account))
                .collect(toList());
    }
    @RequestMapping("/accounts/{id}")
    public AccountDto getById(@PathVariable Long id){
        return new AccountDto(accountRepository.findById(id).orElse(null));
    }

}