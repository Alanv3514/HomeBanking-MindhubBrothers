package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.TransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface TransactionService {

    List<TransactionDto> getAll();

    TransactionDto getById(@PathVariable Long id);

    void makeTransaction(
            @RequestParam Double amount, @RequestParam String description ,
            @RequestParam(value = "fromAccountNumber") String accountFromNumber, @RequestParam String toAccountNumber,
            Authentication authentication);



}
