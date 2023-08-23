package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.enums.CardColor;
import com.mindhub.HomeBanking.models.enums.CardType;
import com.mindhub.HomeBanking.models.enums.LoanType;
import com.mindhub.HomeBanking.models.enums.TransactionType;
import com.mindhub.HomeBanking.models.entities.*;
import com.mindhub.HomeBanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}
@Autowired
private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository ClientRepository,
									  AccountRepository AccountRepository,
									  TransactionRepository TransactionRepository,
									  LoanRepository LoanRepository,
									  ClientLoanRepository ClientLoanRepository,
									  CardRepository CardRepository) {
		return (args) -> {

			Client Melba= new Client("Melba", "Morel","MelMor@email.com", passwordEncoder.encode("melba1234"));
			Client Chloe= new Client("Chloe", "O'Brian","ChlObri@email.com", passwordEncoder.encode("chloe1234"));
			ClientRepository.save(Melba);
			ClientRepository.save(Chloe);

			Account cuenta1= new Account("VIN"+String.format("%03d",AccountRepository.count()+1 ),25000.0,LocalDate.now());
			Melba.addAccount(cuenta1);
			AccountRepository.save(cuenta1);


			Account cuenta2= new Account("VIN"+String.format("%03d", AccountRepository.count()+1),15000.0,LocalDate.now());
			Melba.addAccount(cuenta2);
			AccountRepository.save(cuenta2);

			Account cuenta3= new Account("VIN"+String.format("%03d", AccountRepository.count()+1),20000.0,LocalDate.now());
			Chloe.addAccount(cuenta3);
			AccountRepository.save(cuenta3);

			Transaction transaction1=new Transaction(TransactionType.CREDIT,25000.0,"platita extra");
			Transaction transaction2=new Transaction(TransactionType.DEBIT,500.0,"compra de manaos");

			cuenta1.addTransaction(transaction1);
			cuenta1.addTransaction(transaction2);

			TransactionRepository.save(transaction1);
			TransactionRepository.save(transaction2);

			List<Integer> payments = List.of(2, 4,6,12,24);
			Loan loan1= new Loan(LoanType.mortgage, 400000, payments);
			Loan loan2= new Loan(LoanType.personal, 30000, payments);
			Loan loan3= new Loan(LoanType.automotive, 1000, payments);
			ClientLoan clientLoan1 =new ClientLoan();
			ClientLoan clientLoan2=new ClientLoan();
			ClientLoan clientLoan3=new ClientLoan();
			ClientLoan clientLoan4=new ClientLoan();

			Melba.addClientLoan(clientLoan1);
			Melba.addClientLoan(clientLoan2);
			Melba.addClientLoan(clientLoan3);

			loan1.addClientLoan(clientLoan1);
			loan2.addClientLoan(clientLoan2);
			loan3.addClientLoan(clientLoan3);

			Chloe.addClientLoan(clientLoan4);
			loan2.addClientLoan(clientLoan4);

			LoanRepository.save(loan1);
			LoanRepository.save(loan2);
			LoanRepository.save(loan3);

			ClientLoanRepository.save(clientLoan1);
			ClientLoanRepository.save(clientLoan2);
			ClientLoanRepository.save(clientLoan3);
			ClientLoanRepository.save(clientLoan4);

			Card card1 = new Card(
					CardType.DEBIT,
					CardColor.GOLD,
					"8545-8966-9652-6432",
					521,
					LocalDate.now(),
					LocalDate.now().plusYears(5)
			);
			Card card2 = new Card(
					CardType.CREDIT,
					CardColor.TITANIUM,
					"8545-8342-6437-9472",
					243,
					LocalDate.now(),
					LocalDate.now().plusYears(2)
			);
			Card card3 = new Card(
					CardType.CREDIT,
					CardColor.SILVER,
					"8545-5747-9062-4235",
					993,
					LocalDate.now(),
					LocalDate.now().plusYears(4)
			);

			card1.addCardHolder(Melba);
			card2.addCardHolder(Melba);
			card3.addCardHolder(Chloe);

			CardRepository.save(card1);
			CardRepository.save(card2);
			CardRepository.save(card3);


		};
	}

}
