package com.mindhub.HomeBanking.services.implementations;

import com.mindhub.HomeBanking.dtos.TransactionDto;
import com.mindhub.HomeBanking.models.entities.Transaction;
import com.mindhub.HomeBanking.models.enums.TransactionType;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.TransactionRepository;
import com.mindhub.HomeBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
public class TransactionServicesImplementations implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(transaction -> new TransactionDto(transaction))
                .collect(toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        return new TransactionDto(transactionRepository.findById(id).orElse(null));
    }

    @Override
    public void makeTransaction(Double amount, String description, String accountFromNumber, String toAccountNumber, Authentication authentication) {

        //creamos la transaccion
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT,amount, description);
        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,amount, description);

        Double newBalanceDebit =accountRepository.findByNumber(accountFromNumber).getBalance() - amount;
        Double newBalanceCredit =accountRepository.findByNumber(accountFromNumber).getBalance() + amount;

        //actualizamos el balance de la cuenta origen
        transactionDebit.setBalanceAt(newBalanceDebit);
        accountRepository.findByNumber(accountFromNumber).setBalance(newBalanceDebit);
        accountRepository.findByNumber(accountFromNumber).addTransaction(transactionDebit);
        //actualizamos el balance de la cuenta destino
        transactionCredit.setBalanceAt(newBalanceCredit);
        accountRepository.findByNumber(toAccountNumber).setBalance(newBalanceCredit);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionCredit);
        //guardamos la transaccion
        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);
        //guardamos los cambios en las cuentas
        accountRepository.save(accountRepository.findByNumber(accountFromNumber));
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));
    }
}
