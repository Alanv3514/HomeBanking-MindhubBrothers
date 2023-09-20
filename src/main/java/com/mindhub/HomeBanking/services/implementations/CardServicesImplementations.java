package com.mindhub.HomeBanking.services.implementations;

import com.mindhub.HomeBanking.dtos.AccountDto;
import com.mindhub.HomeBanking.dtos.CardDto;
import com.mindhub.HomeBanking.models.entities.Account;
import com.mindhub.HomeBanking.models.entities.Card;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.models.enums.CardColor;
import com.mindhub.HomeBanking.models.enums.CardType;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.CardRepository;
import com.mindhub.HomeBanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.HomeBanking.utils.utils.genCvv;
import static com.mindhub.HomeBanking.utils.utils.genRandomCardNumber;
import static java.util.stream.Collectors.toList;
@Service
public class CardServicesImplementations implements CardService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;


    @Override
    public List<CardDto> getAllCardsDto() {
        return cardRepository.findAll().stream()
                .map(card -> new CardDto(card))
                .collect(toList());
    }

    @Override
    public CardDto getById(Long id) {
        return new CardDto(cardRepository.findById(id).orElse(null));
    }


    @Override
    public void createCard(CardType cardType, CardColor cardColor, Client AuthClient) {
        Card newCard = new Card(cardType, cardColor, LocalDate.now());

        do {
            newCard.setNumber(genRandomCardNumber());
        } while (cardRepository.findByNumber(newCard.getNumber()) != null);

        newCard.setCvv(genCvv(newCard.getNumber()));

        AuthClient.addCard(newCard);
        cardRepository.save(newCard);

    }
    @Override
    public void deleteCard(CardType cardType, CardColor cardColor, Client AuthClient) {
        Card card = cardRepository.findByActiveIsTrueAndColorAndTypeAndOwner(cardColor,cardType,AuthClient);
        if (card.isActive()) {
            card.switchActive();
            cardRepository.save(card);
    }
    }

}
