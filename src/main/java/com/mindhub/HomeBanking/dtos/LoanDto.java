
package com.mindhub.HomeBanking.dtos;

        import com.mindhub.HomeBanking.models.Loan;
        import java.util.List;

public class LoanDto{

    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;

    public LoanDto(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
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
}
