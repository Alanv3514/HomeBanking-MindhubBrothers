package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.models.Dtos.AccountDto;
import com.mindhub.HomeBanking.models.Dtos.ClientDto;
import com.mindhub.HomeBanking.models.Entities.Account;
import com.mindhub.HomeBanking.models.Entities.Client;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDto> getAll(){
        return accountRepository.findAll().stream()
                .map(account -> new AccountDto(account))
                .collect(toList());
    }
    @RequestMapping("/accounts/{id}")
    public AccountDto getById(@PathVariable Long id, Authentication authentication){
        if(!accountRepository.findById(id).get().getOwner().getEmail().equals(authentication.getName())){
            return null;
        }
        return new AccountDto(accountRepository.findById(id).orElse(null));
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.GET)

    public List<AccountDto> getCurrentAccounts( Authentication authentication) {

        return accountRepository.findAll().stream()
                .filter(account -> account.getOwner().getEmail().equals(authentication.getName()))
                .map(account -> new AccountDto(account))
                .collect(toList());
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> createAccount( Authentication authentication) {


        if (clientRepository.findByEmail(authentication.getName()).getAccounts().stream().count()==3) {

            return new ResponseEntity<>("Already have 3 accounts", HttpStatus.FORBIDDEN);

        }

        Account newAccount= new Account("VIN"+String.format("%03d",accountRepository.count()+1) , 0.0, LocalDate.now());
        Client AuthClient = clientRepository.findByEmail(authentication.getName());
        AuthClient.addAccount(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}