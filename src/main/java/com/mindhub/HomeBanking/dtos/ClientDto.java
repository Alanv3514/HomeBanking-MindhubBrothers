package com.mindhub.HomeBanking.dtos;
import com.mindhub.HomeBanking.models.entities.Client;

import java.util.List;

import static java.util.stream.Collectors.toList;


public class ClientDto  {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private List<AccountDto> accounts;
    private List<ClientLoanDTO> loans;
    private List<CardDto> cards;
    public ClientDto(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts= client.getAccounts().stream()
                .filter(account -> account.isActive())
                .map(AccountDto::new)
                .collect(toList());
        this.loans = client.getClientLoans().stream()
                .map(ClientLoanDTO::new)
                .collect(toList());
        this.cards = client.getCards().stream()
                .map(CardDto::new)
                .collect(toList());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }

    public List<AccountDto> getAccounts() {
        return accounts;
    }
    public List<ClientLoanDTO> getLoans() {
        return loans;
    }
    public List<CardDto> getCards() {
        return cards;
    }
}
