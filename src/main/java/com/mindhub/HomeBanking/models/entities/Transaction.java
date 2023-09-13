package com.mindhub.HomeBanking.models.entities;

import com.mindhub.HomeBanking.models.enums.TransactionType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;

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
    private Double balanceAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;



    public Transaction(){};

    public Transaction(TransactionType type, Double amount, String description) {
        this.type = type;
        this.amount = amount;
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
        return this.type.equals(TransactionType.CREDIT)? amount : -amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDescription() {
        return description + "-" + this.date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBalanceAt() {
        return balanceAt;
    }

    public void setBalanceAt(Double balanceAt) {
        this.balanceAt = balanceAt;
    }
}
