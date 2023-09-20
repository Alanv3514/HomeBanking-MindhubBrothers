package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.ClientLoanRecord;
import com.mindhub.HomeBanking.dtos.LoanDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {

    List<LoanDto> getAll();

    void createLoan(
            ClientLoanRecord clientLoanRecord,
            Authentication authentication);
}
