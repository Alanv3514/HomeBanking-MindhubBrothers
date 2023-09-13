package com.mindhub.HomeBanking.services.implementations;

import com.mindhub.HomeBanking.dtos.TransactionDto;
import com.mindhub.HomeBanking.models.entities.Account;
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
        Account accountFrom = accountRepository.findByNumber(accountFromNumber);
        Account toAccount = accountRepository.findByNumber(toAccountNumber);

        //creamos la transaccion
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT,amount, description);
        Transaction transactionCredit = new Transaction(TransactionType.CREDIT,amount, description);



        Double newBalanceDebit =accountFrom.getBalance() - amount;
        Double newBalanceCredit =toAccount.getBalance() + amount;

        //actualizamos el balance de la cuenta origen
        transactionDebit.setBalanceAt(newBalanceDebit);
        accountFrom.setBalance(newBalanceDebit);
        accountFrom.addTransaction(transactionDebit);
        //actualizamos el balance de la cuenta destino
        transactionCredit.setBalanceAt(newBalanceCredit);
        toAccount.setBalance(newBalanceCredit);
        toAccount.addTransaction(transactionCredit);
        //guardamos la transaccion
        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);
        //guardamos los cambios en las cuentas
        accountRepository.save(accountFrom);
        accountRepository.save(toAccount);
    }
}
