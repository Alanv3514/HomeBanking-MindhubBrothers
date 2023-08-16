package com.mindhub.HomeBanking.models.Entities;

import com.mindhub.HomeBanking.models.Enums.LoanType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ClientLoan{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    private Loan loan;

    public ClientLoan(){}


    public Long getId() {
        return id;
    }



    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

}
