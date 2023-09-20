package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.ClientDto;
import com.mindhub.HomeBanking.dtos.ClientLoanRecord;
import com.mindhub.HomeBanking.dtos.LoanDto;
import com.mindhub.HomeBanking.models.entities.ClientLoan;
import com.mindhub.HomeBanking.models.entities.Transaction;
import com.mindhub.HomeBanking.models.enums.TransactionType;
import com.mindhub.HomeBanking.repositories.*;
import com.mindhub.HomeBanking.services.LoanService;
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
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanService loanService;
    @GetMapping("/loans")
    public List<LoanDto> getAll(){
        return loanService.getAll();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan(
            @RequestBody ClientLoanRecord clientLoanRecord,
            Authentication authentication){

        Long loanId = clientLoanRecord.getLoanId();
        Double amount = clientLoanRecord.getAmount();
        Integer payments = clientLoanRecord.getPayments();
        String toAccountNumber = clientLoanRecord.getToAccountNumber();

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

        loanService.createLoan(clientLoanRecord,authentication);

        return  new ResponseEntity<>(HttpStatus.CREATED);
    }



}
