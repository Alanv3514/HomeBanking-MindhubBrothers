
package com.mindhub.HomeBanking.dtos;

        import com.mindhub.HomeBanking.models.entities.Loan;
        import com.mindhub.HomeBanking.models.entities.Payment;

        import java.util.ArrayList;
        import java.util.List;

public class LoanDto{

    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;
    private List<Integer> rate;

    public LoanDto(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = new ArrayList<>();
        this.rate = new ArrayList<>();
        for (Payment payment : loan.getPayments()) {
            this.payments.add(payment.getAmountPayment());
            this.rate.add(payment.getInterestRate());
        }

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public List<Integer> getRate() {
        return rate;
    }
}
