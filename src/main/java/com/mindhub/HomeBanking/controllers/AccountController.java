package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.AccountDto;
import com.mindhub.HomeBanking.models.entities.Account;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import com.mindhub.HomeBanking.services.AccountService;
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
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDto> getAll(){
        return accountService.getAll();
    }
    @GetMapping("/accounts/{id}")
    public AccountDto getById(@PathVariable Long id, Authentication authentication){

        if(!accountRepository.findById(id).get().getOwner().getEmail().equals(authentication.getName())){
            return null;
        }

        return accountService.getById(id, authentication);
    }
    @GetMapping("/clients/current/accounts")

    public List<AccountDto> getCurrentAccounts( Authentication authentication) {
        System.out.println(accountService.getCurrentAccounts(authentication).stream().toString());
        return accountService.getCurrentAccounts(authentication);
    }

    @PostMapping("/clients/current/accounts")

    public ResponseEntity<Object> createAccount( Authentication authentication) {


        if (clientRepository.findByEmail(authentication.getName()).getAccounts().stream().filter(account -> account.isActive()).count()==3) {

            return new ResponseEntity<>("Already have 3 actives accounts", HttpStatus.FORBIDDEN);

        }

        accountService.createAccount(authentication);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @DeleteMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount (@RequestParam String accountNumber, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = client.getAccounts().stream()
                .filter(a -> a.getNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);

        if(client.getAccounts().stream().filter(account1 -> account1.isActive()).count()==1){
            return new ResponseEntity<>("Cannot delete all accounts",HttpStatus.FORBIDDEN);
        }

        if (account==null){
            return new ResponseEntity<>("Account number does not belong to the authenticated user", HttpStatus.FORBIDDEN);
        }

        accountService.deactivateAccount(authentication,accountNumber);

        return new ResponseEntity<>("Delete account succesful",HttpStatus.OK);
    }
}