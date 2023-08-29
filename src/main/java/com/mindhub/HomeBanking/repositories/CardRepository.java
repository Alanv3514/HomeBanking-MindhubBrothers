package com.mindhub.HomeBanking.repositories;

import com.mindhub.HomeBanking.models.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    //findByNumber
    Card findByNumber(String number);
}
