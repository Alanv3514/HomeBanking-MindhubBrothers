package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.Account;

import java.time.LocalDate;

public class AccountDto {
    private Long id;
    private String number;
    private Double balance;
    private LocalDate date;


    public AccountDto(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.date = account.getDate();
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

    public LocalDate getDate(){
        return date;
    }
}
