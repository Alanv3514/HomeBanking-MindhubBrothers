package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.CardDto;
import com.mindhub.HomeBanking.models.entities.Card;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.models.enums.CardColor;
import com.mindhub.HomeBanking.models.enums.CardType;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.CardRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import com.mindhub.HomeBanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.HomeBanking.utils.utils.genCvv;
import static com.mindhub.HomeBanking.utils.utils.genRandomCardNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardService cardService;
    @GetMapping("/cards")
    public List<CardDto> getAll(){
        return cardService.getAllCardsDto();
    }
    @GetMapping("/cards/{id}")
    public CardDto getById(@PathVariable Long id){
        return cardService.getById(id);
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam  CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client AuthClient = clientRepository.findByEmail(authentication.getName());



        if (cardRepository.findByOwner(AuthClient).stream().filter(card -> card.isActive())
                .anyMatch(card -> card.getType().equals(cardType) && card.getColor().equals(cardColor))) {
            return new ResponseEntity<>("Already have a "+cardType+" card "+cardColor+".", HttpStatus.FORBIDDEN);
        }


        cardService.createCard(cardType, cardColor, AuthClient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/clients/current/cards")
    public ResponseEntity<Object> deleteCard(@RequestParam  CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {

        Client AuthClient = clientRepository.findByEmail(authentication.getName());


        cardService.deleteCard(cardType, cardColor, AuthClient);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}