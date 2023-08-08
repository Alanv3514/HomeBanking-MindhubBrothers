package com.mindhub.HomeBanking.dtos;
import com.mindhub.HomeBanking.models.Client;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class ClientDto  {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private List<AccountDto> accounts;

    public ClientDto(Client client) {

        this.id = client.getId();

        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts= client.getAccounts().stream()
                .map(AccountDto::new)
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
}
