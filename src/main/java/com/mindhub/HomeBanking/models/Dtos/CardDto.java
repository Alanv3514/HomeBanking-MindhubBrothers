package com.mindhub.HomeBanking.models.Dtos;

import com.mindhub.HomeBanking.models.Enums.CardColor;
import com.mindhub.HomeBanking.models.Enums.CardType;
import com.mindhub.HomeBanking.models.Entities.Card;

import java.time.LocalDate;

public class CardDto {

    private Long id;
    private CardType type;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDto(Card card) {
        this.id = card.getId();
        this.type = card.getType();
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
