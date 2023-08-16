package com.mindhub.HomeBanking.models.Dtos;

import com.mindhub.HomeBanking.models.Enums.TransactionType;
import com.mindhub.HomeBanking.models.Entities.Transaction;

import java.time.LocalDate;


public class TransactionDto {

    private Long id;
    private TransactionType type;
    private Double amount;
    private LocalDate date;
    private String description;
    public TransactionDto(){};

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
