package com.mindhub.HomeBanking.repositories;


import com.mindhub.HomeBanking.models.entities.Account;
import com.mindhub.HomeBanking.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber(String number);
    Client findByOwner(Client owner);
}
