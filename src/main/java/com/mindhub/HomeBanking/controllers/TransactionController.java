package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.models.Dtos.TransactionDto;
import com.mindhub.HomeBanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @RequestMapping(value = "/transactions",method = RequestMethod.GET)
    public List<TransactionDto> getAll(){
        return transactionRepository.findAll().stream()
                .map(transaction -> new TransactionDto(transaction))
                .collect(toList());
    }
    @RequestMapping(value = "/transactions/{id}",method = RequestMethod.GET)
    public TransactionDto getById(@PathVariable Long id){
        return new TransactionDto(transactionRepository.findById(id).orElse(null));
    }
}






