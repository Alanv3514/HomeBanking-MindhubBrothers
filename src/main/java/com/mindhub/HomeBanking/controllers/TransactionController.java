package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.TransactionDto;
import com.mindhub.HomeBanking.models.entities.Transaction;
import com.mindhub.HomeBanking.models.enums.TransactionType;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
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
    @Transactional
    @RequestMapping(value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity makeTransaction(
            @RequestParam Double amount, @RequestParam String description ,
            @RequestParam(value = "fromAccountNumber") String accountFromNumber, @RequestParam String toAccountNumber,
            Authentication authentication){


        //verificamos que los parametros no esten vacios
        if (amount == null || description == null || accountFromNumber == null || toAccountNumber == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        //verificamos que el numero de cuenta de origen y destino no sean iguales
        if (accountFromNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("The origin and destination accounts are the same", HttpStatus.FORBIDDEN);
        }
        //verificamos que el numero de cuenta de origen exista
        if (accountRepository.findByNumber(accountFromNumber) == null) {
            return new ResponseEntity<>("The origin account does not exist", HttpStatus.FORBIDDEN);
        }
        //verificamos que el numero de cuenta de destino exista
        if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }
        //verificamos que el numero de cuenta de origen pertenezca al cliente logueado
        if (!accountRepository.findByNumber(accountFromNumber).getOwner().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("The origin account does not belong to the logged in client", HttpStatus.FORBIDDEN);
        }
        //vericiamos que hayta fondos en la cuenta origen
        if (accountRepository.findByNumber(accountFromNumber).getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        //verificamos que el monto sea mayor a 0
        if (amount <= 0) {
            return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
        }

        //creamos la transaccion
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT,amount, description);
        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,amount, description);

        //actualizamos el balance de la cuenta origen
        accountRepository.findByNumber(accountFromNumber).setBalance(accountRepository.findByNumber(accountFromNumber).getBalance() - amount);
        accountRepository.findByNumber(accountFromNumber).addTransaction(transactionDebit);
        //actualizamos el balance de la cuenta destino
        accountRepository.findByNumber(toAccountNumber).setBalance(accountRepository.findByNumber(toAccountNumber).getBalance() + amount);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionCredit);
        //guardamos la transaccion
        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);
        //guardamos los cambios en las cuentas
        accountRepository.save(accountRepository.findByNumber(accountFromNumber));
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }



}






