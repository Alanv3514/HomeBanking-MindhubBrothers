package com.mindhub.HomeBanking.repositories;

import com.mindhub.HomeBanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
