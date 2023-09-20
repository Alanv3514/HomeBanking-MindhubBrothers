package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.entities.Account;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class AccountDto {
    private Long id;
    private String number;
    private Double balance;
    private LocalDate creationDate;
    private List<TransactionDto> transactions;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.creationDate = account.getDate();
        this.transactions= account.getTransactions().stream()
                .map(TransactionDto::new)
                .collect(toList());
    }

    public Long getId(){
        return id;
    }
    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }
}
