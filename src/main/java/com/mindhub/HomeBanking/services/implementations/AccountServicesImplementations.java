package com.mindhub.HomeBanking.services.implementations;

import com.mindhub.HomeBanking.dtos.AccountDto;
import com.mindhub.HomeBanking.models.entities.Account;
import com.mindhub.HomeBanking.models.entities.Client;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import com.mindhub.HomeBanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServicesImplementations implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDto> getAll() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountDto(account))
                .collect(toList());
    }

    @Override
    public AccountDto getById(Long id, Authentication authentication) {
        return new AccountDto(accountRepository.findById(id).orElse(null));
    }

    @Override
    public List<AccountDto> getCurrentAccounts(Authentication authentication) {
            List<AccountDto> listita =accountRepository.findByActiveTrue().stream()
                    .filter(account -> account.getOwner().getEmail().equals(authentication.getName()))
                    .map(account -> new AccountDto(account))
                    .collect(toList());
        return listita;
    }


    @Override
    public void createAccount(Authentication authentication) {
        Account newAccount= new Account("VIN"+String.format("%03d",accountRepository.count()+1) , 0.0, LocalDate.now());
        Client AuthClient = clientRepository.findByEmail(authentication.getName());
        AuthClient.addAccount(newAccount);
        accountRepository.save(newAccount);
    }

    @Override
    public void deactivateAccount(Authentication authentication, String accountNumber){
        Account account = accountRepository.findByNumber(accountNumber);
        if (account.isActive()) {
            account.switchActive();
            accountRepository.save(account);
    }
    }
}
