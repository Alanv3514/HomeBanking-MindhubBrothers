package com.mindhub.HomeBanking.models.Dtos;

import com.mindhub.HomeBanking.models.Entities.ClientLoan;

public class ClientLoanDTO {

    private Long id;
    private String name;
    private Double amount;
    private Integer payments;

    private long loanId;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
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
