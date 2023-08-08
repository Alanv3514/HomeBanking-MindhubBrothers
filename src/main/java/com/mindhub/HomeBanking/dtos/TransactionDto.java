package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.enums.TransactionType;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.Transaction;


public class TransactionDto {

    private Long id;
    private TransactionType type;
    private Double amount;

    public TransactionDto(){};

    public TransactionDto(Transaction transaction) {
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
    }


}
