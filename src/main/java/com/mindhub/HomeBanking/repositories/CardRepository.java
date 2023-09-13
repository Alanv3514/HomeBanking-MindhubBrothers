package com.mindhub.HomeBanking.repositories;

import com.mindhub.HomeBanking.models.entities.Card;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.models.enums.CardColor;
import com.mindhub.HomeBanking.models.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.ArrayList;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByNumber(String number);
    ArrayList<Card> findByOwner(Client authClient);
    Card findByActiveIsTrueAndColorAndTypeAndOwner(CardColor cardColor, CardType cardType, Client client);
}
