package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.ClientDto;
import com.mindhub.HomeBanking.models.entities.Account;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @RequestMapping("/clients")
    public List<ClientDto> getAll(){
        return clientRepository.findAll().stream()
                .map(client -> new ClientDto(client))
                .collect(toList());
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {


        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }


        if (clientRepository.findByEmail(email) != null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        Account account= new Account("VIN"+String.format("%03d",accountRepository.count()+1) , 0.0, LocalDate.now());
        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        newClient.addAccount(account);
        clientRepository.save(newClient);
        accountRepository.save(account);



        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @RequestMapping("/clients/{id}")
    public ClientDto getById(@PathVariable Long id){
        return new ClientDto(clientRepository.findById(id).orElse(null));
    }

    @RequestMapping("/clients/current")
    public ClientDto getCurrentClient(Authentication authentication) {
        return new ClientDto(clientRepository.findByEmail(authentication.getName()));
    }


}
