package com.mindhub.HomeBanking.repositories;

import com.mindhub.HomeBanking.models.entities.Card;
import com.mindhub.HomeBanking.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.ArrayList;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    //findByNumber
    Card findByNumber(String number);

    ArrayList<Card> findByOwner(Client authClient);
}
