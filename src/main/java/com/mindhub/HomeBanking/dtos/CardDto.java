package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.enums.CardColor;
import com.mindhub.HomeBanking.models.enums.CardType;
import com.mindhub.HomeBanking.models.entities.Card;

import java.time.LocalDate;

public class CardDto {

    private Long id;
    private CardType type;
    private String cardHolder;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDto(Card card) {
        this.id = card.getId();
        this.type = card.getType();
        this.cardHolder =card.getCardHolder();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public Long getId() {
        return id;
    }



    public CardType getType() {
        return type;
    }

    public String getCardHolder() {
        return cardHolder;
    }
    public CardColor getColor() {
        return color;
    }


    public String getNumber() {
        return number;
    }


    public Integer getCvv() {
        return cvv;
    }


    public LocalDate getFromDate() {
        return fromDate;
    }



    public LocalDate getThruDate() {
        return thruDate;
    }

}
