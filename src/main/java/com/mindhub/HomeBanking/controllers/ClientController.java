package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.ClientDto;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping(value = "/clients",method = RequestMethod.GET)
    public List<ClientDto> getAll(){
        return clientRepository.findAll().stream()
                .map(client -> new ClientDto(client))
                .collect(toList());
    }
    @RequestMapping(value = "/clients/{id}",method = RequestMethod.GET)
    public ClientDto getById(@PathVariable Long id){
        return new ClientDto(clientRepository.findById(id).orElse(null));
    }

}
