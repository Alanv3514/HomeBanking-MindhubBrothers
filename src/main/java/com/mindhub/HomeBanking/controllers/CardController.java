package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.models.Dtos.CardDto;
import com.mindhub.HomeBanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @RequestMapping("/cards")
    public List<CardDto> getAll(){
        return cardRepository.findAll().stream()
                .map(card -> new CardDto(card))
                .collect(toList());
    }
    @RequestMapping("/cards/{id}")
    public CardDto getById(@PathVariable Long id){
        return new CardDto(cardRepository.findById(id).orElse(null));
    }

}