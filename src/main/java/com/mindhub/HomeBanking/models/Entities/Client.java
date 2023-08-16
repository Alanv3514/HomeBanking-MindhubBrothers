package com.mindhub.HomeBanking.models.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="cardHolder", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client(){};

    public Client( String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }


    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        account.setOwner(this);
        accounts.add(account);
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public Set<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        card.addCardHolder(this);
        cards.add(card);
    }


}
