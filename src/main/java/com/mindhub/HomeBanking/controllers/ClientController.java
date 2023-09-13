package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.ClientDto;
import com.mindhub.HomeBanking.models.entities.Account;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import com.mindhub.HomeBanking.services.ClientService;
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
    private ClientService clientService;
    @GetMapping("/clients")
    public List<ClientDto> getAll(){
        return clientService.getClientsDTO();
    }

    @PostMapping("/clients")

    public ResponseEntity<Object> saveClient(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing Name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()) {
            return new ResponseEntity<>("Missing Lastname", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("Missing Email", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("Missing Password", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        clientService.saveClient(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/clients/{id}")
    public ClientDto getById(@PathVariable Long id){
        return clientService.getClientsDTOById(id);
    }

    @GetMapping("/clients/current")
    public ClientDto getCurrentClient(Authentication authentication) {
        return clientService.getCurrentClient(authentication);
    }


}
