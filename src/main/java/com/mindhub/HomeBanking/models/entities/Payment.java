package com.mindhub.HomeBanking.models.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Payment {
    private Integer amountPayment;
    private Integer interestRate;

    public Payment(){};

    public Payment(Integer amountPayment, Integer interestRate) {
        this.amountPayment = amountPayment;
        this.interestRate = interestRate;
    }

    public Integer getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(Integer paymentType) {
        this.amountPayment = paymentType;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }
}