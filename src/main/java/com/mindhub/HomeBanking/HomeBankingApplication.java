package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.enums.CardColor;
import com.mindhub.HomeBanking.models.enums.CardType;
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

import static com.mindhub.HomeBanking.utils.utils.*;

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
			Client Admin= new Client("admin", "admin","admin@email.com", passwordEncoder.encode("admin"));
			ClientRepository.save(Melba);
			ClientRepository.save(Chloe);

			Admin.setRole("ADMIN");
			ClientRepository.save(Admin);

			Account cuenta1= new Account(genAccountId(AccountRepository),25000.0,LocalDate.now());
			Melba.addAccount(cuenta1);
			AccountRepository.save(cuenta1);


			Account cuenta2= new Account(genAccountId(AccountRepository),15000.0,LocalDate.now());
			Melba.addAccount(cuenta2);
			AccountRepository.save(cuenta2);

			Account cuenta3= new Account(genAccountId(AccountRepository),20000.0,LocalDate.now());
			Chloe.addAccount(cuenta3);
			AccountRepository.save(cuenta3);

			Transaction transaction1=new Transaction(TransactionType.CREDIT,25000.0,"platita extra");
			Double newBalance1 =cuenta1.getBalance() + transaction1.getAmount();
			transaction1.setBalanceAt(newBalance1);
			cuenta1.addTransaction(transaction1);
			TransactionRepository.save(transaction1);
			AccountRepository.save(cuenta1);

			Transaction transaction2=new Transaction(TransactionType.DEBIT,500.0,"compra de manaos");
			Double newBalance2 =cuenta1.getBalance() - transaction1.getAmount();
			transaction2.setBalanceAt(newBalance2);
			cuenta1.setBalance(newBalance2);
			cuenta1.addTransaction(transaction2);
			TransactionRepository.save(transaction2);

			AccountRepository.save(cuenta1);

			Payment payment2= new Payment(2,15);
			Payment payment4= new Payment(4,20);
			Payment payment6= new Payment(8,50);
			Payment payment12= new Payment(12,100);
			Payment payment24= new Payment(24,224);
			Payment payment48= new Payment(48,400);
			Payment payment60= new Payment(60,625);

			List<Payment> paymentsPersonal = List.of(payment2, payment4,payment6,payment12);
			List<Payment> paymentsMortgage = List.of(payment12, payment24,payment48,payment60);
			List<Payment> paymentsAutomotive = List.of(payment6, payment12,payment24,payment48);

			Loan loan1= new Loan("mortgage", 400000, paymentsMortgage);
			Loan loan2= new Loan("personal", 30000, paymentsPersonal);
			Loan loan3= new Loan("automotive", 1000, paymentsAutomotive);
			ClientLoan clientLoan1 =new ClientLoan(100.0, 12);
			ClientLoan clientLoan2=new ClientLoan(100.0, 12);
			ClientLoan clientLoan3=new ClientLoan(100.0, 12);
			ClientLoan clientLoan4=new ClientLoan(100.0, 12);

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
					LocalDate.now()

			);
			Card card2 = new Card(
					CardType.CREDIT,
					CardColor.TITANIUM,
					LocalDate.now()

			);
			Card card3 = new Card(
					CardType.CREDIT,
					CardColor.SILVER,
					LocalDate.now()

			);

			card1.setNumber(genRandomCardNumber());
			card1.setCvv(genCvv(card1.getNumber()));
			card1.addCardHolder(Melba);
			CardRepository.save(card1);

			card2.setNumber(genRandomCardNumber());
			card2.setCvv(genCvv(card2.getNumber()));
			card2.addCardHolder(Melba);
			CardRepository.save(card2);

			card3.setNumber(genRandomCardNumber());
			card3.setCvv(genCvv(card3.getNumber()));
			card3.addCardHolder(Chloe);
			CardRepository.save(card3);












		};
	}

}
