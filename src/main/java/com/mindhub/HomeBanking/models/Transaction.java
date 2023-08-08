package com.mindhub.HomeBanking.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mindhub.HomeBanking.enums.TransactionType;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transaction_id")
    private Account account;

    public Transaction(){};

    public Transaction(Account account, TransactionType type, Double amount, String description) {
        this.type = type;
        setAmount( amount, type);
        this.account = account;
        this.account.addTransaction(this);
        this.description = description;
        this.date = LocalDate.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount, TransactionType type) {

        this.amount = this.type.equals(TransactionType.CREDIT)? amount : -amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
