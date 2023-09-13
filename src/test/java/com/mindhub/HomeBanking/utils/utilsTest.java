package com.mindhub.HomeBanking.utils;

import com.mindhub.HomeBanking.utils.utils;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class utilsTest {

    @Test
    void genRandomCardNumber() {
        String cardNumber = utils.genRandomCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    void genCvv() {
        String cardNumber = utils.genRandomCardNumber();
        Integer cvv = utils.genCvv(cardNumber);
        assertThat(cvv,is(not(nullValue())));
    }

    @Autowired
    AccountRepository accountRepository;
    @Test
    void genAccountId() {
        String accountId = utils.genAccountId(accountRepository);
        assertThat(accountId,is(not(emptyOrNullString())));
    }
}