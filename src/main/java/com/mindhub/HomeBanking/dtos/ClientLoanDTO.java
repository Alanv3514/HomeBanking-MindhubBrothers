package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.entities.ClientLoan;

import java.util.List;

public class ClientLoanDTO {

    private Long id;
    private String name;
    private Double amount;
    private Integer payments;

    private long loanId;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getType();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
