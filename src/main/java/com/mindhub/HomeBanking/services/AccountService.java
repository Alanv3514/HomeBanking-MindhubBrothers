package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAll();

    AccountDto getById(Long id, Authentication authentication);

    List<AccountDto> getCurrentAccounts( Authentication authentication);


    void createAccount(Authentication authentication);

    void deactivateAccount(Authentication authentication, String accountNumber);
}
