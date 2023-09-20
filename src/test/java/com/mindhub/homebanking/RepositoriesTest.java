package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.entities.*;
import com.mindhub.HomeBanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {



    @Autowired
    AccountRepository accountRepository;
    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void orphanAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem(
                hasProperty("owner", is(not(nullValue())))));
    }

    @Autowired
    CardRepository cardRepository;
    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    @Test
    public void validCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(
                hasProperty("number",is(not("^\\d{16}$\n")))));
    }

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }

    @Test
    public void existAdmin(){
        Client admin = clientRepository.findByRole("ADMIN");
        assertThat(admin,hasProperty("role",is("ADMIN")));
    }

    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Test
    public void existClienLoans(){
        List<ClientLoan> clientLoans = clientLoanRepository.findAll();
        assertThat(clientLoans,is(not(empty())));
    }
    @Test
    public void emptyAmountClientLoan(){
        List<ClientLoan> clientLoans = clientLoanRepository.findAll();
        assertThat(clientLoans,hasItem(
                hasProperty("amount",is(not(nullValue())))
        ));
    }

    @Autowired
    LoanRepository loanRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(
                hasProperty("name", is("personal"))));
    }
    @Autowired
    TransactionRepository transactionRepository;
    @Test
    public void isNotAdminTransaction(){
        List<Transaction> transactions= transactionRepository.findAll();
        assertThat(transactions, hasItem(
                hasProperty("account",
                        hasProperty("owner",
                                hasProperty("role",is(not("ADMIN")))))
        ));
    }
    @Test
    public void isNotEmptyTransaction(){
        List<Transaction> transactions= transactionRepository.findAll();
        assertThat(transactions,
                hasItem(
                        hasProperty("amount",is(not(nullValue())))
                ));
    }
}