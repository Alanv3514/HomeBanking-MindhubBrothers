package com.mindhub.HomeBanking.repositories;


import com.mindhub.HomeBanking.models.Entities.Account;
import com.mindhub.HomeBanking.models.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {


    Client findByOwner(Client owner);
}
