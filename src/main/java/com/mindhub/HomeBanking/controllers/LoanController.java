package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.ClientDto;
import com.mindhub.HomeBanking.dtos.ClientLoanRecord;
import com.mindhub.HomeBanking.dtos.LoanDto;
import com.mindhub.HomeBanking.models.entities.ClientLoan;
import com.mindhub.HomeBanking.models.entities.Transaction;
import com.mindhub.HomeBanking.models.enums.TransactionType;
import com.mindhub.HomeBanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @RequestMapping(value="/loans",method = RequestMethod.GET)
    public List<LoanDto> getAll(){
        return loanRepository.findAll().stream()
                .map(loan -> new LoanDto(loan))
                .collect(toList());
    }

    @Transactional
    @RequestMapping(value="/loans",method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(
            @RequestBody ClientLoanRecord clientLoanRecord,
            Authentication authentication){

        Long loanId = clientLoanRecord.getLoanId();
        Double amount = clientLoanRecord.getAmount();
        Integer payments = clientLoanRecord.getPayments();
        String toAccountNumber = clientLoanRecord.getToAccountNumber();

        System.out.println("loan: "+ loanRepository.findById(loanId).get());
        System.out.println("amount: "+amount);
        System.out.println("payments: "+payments);
        System.out.println("toAccountNumber: "+toAccountNumber);
        if (loanId==null || amount == null || payments == null || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0) {
            return new ResponseEntity<>("Amount must be positive", HttpStatus.FORBIDDEN);
        }
        if (payments <= 0) {
            return new ResponseEntity<>("Payments must be positive", HttpStatus.FORBIDDEN);
        }
        if (!loanRepository.findById(loanId).isPresent() ) {
            return new ResponseEntity<>("Loan not exist", HttpStatus.FORBIDDEN);
        }
        if (accountRepository.findByNumber(toAccountNumber) == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.findByNumber(toAccountNumber).getOwner().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("The destination account does not belong to the logged user", HttpStatus.FORBIDDEN);
        }

        if(amount>loanRepository.findById(loanId).get().getMaxAmount()){
            return new ResponseEntity<>("The amount is greater than the maximum allowed", HttpStatus.FORBIDDEN);
        }

        clientRepository.findByEmail(authentication.getName());
        ClientLoan clientLoan =new ClientLoan(amount,payments);


        clientRepository.findByEmail(authentication.getName()).addClientLoan(clientLoan);
        loanRepository.findById(loanId).get().addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);

        //creamos la transaccion
        Transaction transactionLoan = new Transaction(TransactionType.CREDIT,amount, "loan approved");

        //actualizamos el balance de la cuenta destino
        accountRepository.findByNumber(toAccountNumber).setBalance(accountRepository.findByNumber(toAccountNumber).getBalance() + amount);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionLoan);
        //guardamos la transaccion
        transactionRepository.save(transactionLoan);
        //guardamos los cambios en las cuentas
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));

        return  new ResponseEntity<>(HttpStatus.CREATED);
    }



}
