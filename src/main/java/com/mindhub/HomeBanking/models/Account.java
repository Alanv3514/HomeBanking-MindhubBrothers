package com.mindhub.HomeBanking.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private Double balance;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    @JsonIgnoreProperties(value = "accounts")
    private Client owner;


    public Account(){};

    public Account(Client owner, Double balance, LocalDate date) {
        this.setOwner(owner);
        owner.getAccounts().add(this);
        this.number="VIN"+String.format("%03d",this.getOwner().getAccounts().size());
        this.balance = balance;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", number=" + number +
                ", date=" + date +
                '}';
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {

        this.owner = owner;
    }

}
