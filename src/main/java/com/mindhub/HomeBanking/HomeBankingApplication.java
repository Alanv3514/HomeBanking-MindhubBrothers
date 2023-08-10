package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.enums.TransactionType;
import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository ClientRepository, AccountRepository AccountRepository, TransactionRepository TransactionRepository, LoanRepository LoanRepository, ClientLoanRepository ClientLoanRepository) {
		return (args) -> {

			Client Melba= new Client("Melba", "Morel","MelMor@email.com");
			Client Chloe= new Client("Chloe", "O'Brian","ChlObri@email.com");
			ClientRepository.save(Melba);
			ClientRepository.save(Chloe);

			AccountRepository.save(new Account(Melba, 5000.0, LocalDate.now()));
			AccountRepository.save(new Account(Melba, 7500.0, LocalDate.now().plusDays(1)));
			AccountRepository.save(new Account(Chloe, 100.0, LocalDate.now()));
			AccountRepository.save(new Account(Chloe, 20.0, LocalDate.now()));

			TransactionRepository.save(new Transaction(AccountRepository.findById(1L).get(), TransactionType.CREDIT, 1234.0, "Cobro prestamo"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(1l).get(), TransactionType.DEBIT, 24.0, "Compra Caramelo"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(1L).get(), TransactionType.CREDIT, 2500.0, "Cobro de Changa"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(1L).get(), TransactionType.DEBIT, 3000.0, "Pan y Leche"));

			TransactionRepository.save(new Transaction(AccountRepository.findById(2L).get(), TransactionType.CREDIT, 1234.0, "lorem ipsum dolor sit amet,"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(2L).get(), TransactionType.DEBIT, 24.0, " consectetur adipiscing elit,"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(2L).get(), TransactionType.CREDIT, 2500.0, "sed do eiusmod tempor incididunt ut"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(2L).get(), TransactionType.DEBIT, 3000.0, "  labore et dolore magna aliqua."));

			TransactionRepository.save(new Transaction(AccountRepository.findById(3L).get(), TransactionType.CREDIT, 1234.0, "lorem ipsum dolor sit amet,"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(3L).get(), TransactionType.DEBIT, 24.0, " consectetur adipiscing elit,"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(4L).get(), TransactionType.CREDIT, 2500.0, "sed do eiusmod tempor incididunt ut"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(3L).get(), TransactionType.DEBIT, 3000.0, "  labore et dolore magna aliqua."));

			TransactionRepository.save(new Transaction(AccountRepository.findById(1L).get(), TransactionType.CREDIT, 1234.0, "Deposito inicial"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(4L).get(), TransactionType.DEBIT, 24.0, " consectetur adipiscing elit,"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(2L).get(), TransactionType.CREDIT, 2500.0, "sed do eiusmod tempor incididunt ut"));
			TransactionRepository.save(new Transaction(AccountRepository.findById(3L).get(), TransactionType.DEBIT, 3000.0, "  labore et dolore magna aliqua."));
			List<String> credits = List.of("mortgage", "personal","automotive credit");
			List<Integer> payments = List.of(2, 4,6,12,24,32,48,60);
			for (String i: credits){
				LoanRepository.save(new Loan(i, 40000, payments));
			}

			ClientLoan MelbaClientLoan = new ClientLoan(LoanRepository.findById(1L).get(), 40000.0, 60);
			ClientLoan ChloeClientLoan = new ClientLoan(LoanRepository.findById(2L).get(), 40000.0, 60);

			MelbaClientLoan.getClients().add(Melba);
			ChloeClientLoan.getClients().add(Chloe);

			Melba.addClientLoan(MelbaClientLoan);
			Chloe.addClientLoan(ChloeClientLoan);

			ClientLoanRepository.save(MelbaClientLoan);
			ClientLoanRepository.save(ChloeClientLoan);


		};
	}

}
