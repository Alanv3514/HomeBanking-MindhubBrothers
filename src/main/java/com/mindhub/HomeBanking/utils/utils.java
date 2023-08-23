package com.mindhub.HomeBanking.utils;

import com.mindhub.HomeBanking.repositories.AccountRepository;

import java.util.Random;

public class utils {




    public static String genAccountId(AccountRepository accountRepository) {
        return "VIN"+String.format("%03d",accountRepository.count()+1 );
    }

}
