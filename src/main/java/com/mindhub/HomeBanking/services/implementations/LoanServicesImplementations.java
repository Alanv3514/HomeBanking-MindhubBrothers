package com.mindhub.HomeBanking.services.implementations;

import com.mindhub.HomeBanking.dtos.ClientLoanRecord;
import com.mindhub.HomeBanking.dtos.LoanDto;
import com.mindhub.HomeBanking.models.entities.ClientLoan;
import com.mindhub.HomeBanking.models.entities.Transaction;
import com.mindhub.HomeBanking.models.enums.TransactionType;
import com.mindhub.HomeBanking.repositories.*;
import com.mindhub.HomeBanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
public class LoanServicesImplementations implements LoanService {

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

    @Override
    public List<LoanDto> getAll() {
        return loanRepository.findAll().stream()
                .map(loan -> new LoanDto(loan))
                .collect(toList());
    }

    @Override
    public void createLoan(ClientLoanRecord clientLoanRecord, Authentication authentication) {
        Long loanId = clientLoanRecord.getLoanId();
        Double amount = clientLoanRecord.getAmount();
        Integer payments = clientLoanRecord.getPayments();
        String toAccountNumber = clientLoanRecord.getToAccountNumber();


        clientRepository.findByEmail(authentication.getName());
        ClientLoan clientLoan =new ClientLoan(amount,payments);


        clientRepository.findByEmail(authentication.getName()).addClientLoan(clientLoan);
        loanRepository.findById(loanId).get().addClientLoan(clientLoan);

        clientLoanRepository.save(clientLoan);

        //creamos la transaccion
        Transaction transactionLoan = new Transaction(TransactionType.CREDIT,amount, "loan approved");

        Double newBalance=accountRepository.findByNumber(toAccountNumber).getBalance() + amount;
        transactionLoan.setBalanceAt(newBalance);
        //actualizamos el balance de la cuenta destino
        accountRepository.findByNumber(toAccountNumber).setBalance(accountRepository.findByNumber(toAccountNumber).getBalance() + amount);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionLoan);
        //guardamos la transaccion
        transactionRepository.save(transactionLoan);
        //guardamos los cambios en las cuentas
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));
    }
}
