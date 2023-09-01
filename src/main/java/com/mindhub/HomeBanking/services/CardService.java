package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.AccountDto;
import com.mindhub.HomeBanking.dtos.CardDto;
import com.mindhub.HomeBanking.dtos.ClientDto;
import com.mindhub.HomeBanking.models.entities.Account;
import com.mindhub.HomeBanking.models.entities.Card;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.models.enums.CardColor;
import com.mindhub.HomeBanking.models.enums.CardType;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {
    List<CardDto> getAllCardsDto();

    CardDto getById(Long id);

    void createCard(CardType cardType, CardColor cardColor, Client AuthClient);
}
