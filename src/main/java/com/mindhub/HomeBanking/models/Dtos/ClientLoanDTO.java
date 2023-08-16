package com.mindhub.HomeBanking.models.Dtos;

import com.mindhub.HomeBanking.models.Entities.ClientLoan;
import com.mindhub.HomeBanking.models.Enums.LoanType;

import java.util.List;

public class ClientLoanDTO {

    private Long id;
    private String name;
    private Double amount;
    private List<Integer> payments;

    private long loanId;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getType().name();
        this.amount = clientLoan.getLoan().getMaxAmount();
        this.payments = clientLoan.getLoan().getPayments();
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

    public List<Integer> getPayments() {
        return payments;
    }
}
