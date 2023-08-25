package com.mindhub.HomeBanking.utils;

import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class utils {

    public static String genRandomCardNumber(CardRepository cardRepository) {
        String cardNumber = "8545";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            cardNumber += "-" + String.format("%04d", random.nextInt(10000));
        }
        if (!cardRepository.findByNumber(cardNumber)) {
            return cardNumber;
        } else {
            return genRandomCardNumber(cardRepository);
        }
    }
    public static Integer genCvv(String cardNumber) {
        int sum = 0;
        for (String bloque : cardNumber.split("-")) {
            int aux = 0;
            for (int i = 0; i < 3; i++) {
                aux += Character.getNumericValue(bloque.charAt(i));
            }
            sum += aux;
        }
        return (300+(sum % 10000));
    }

    public static String genAccountId(AccountRepository accountRepository) {
        return "VIN"+String.format("%03d",accountRepository.count()+1 );
    }

}
