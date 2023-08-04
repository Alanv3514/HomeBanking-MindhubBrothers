package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeBankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository repository) {
		return (args) -> {
			repository.save(new Client("Jack", "Bauer","JacBav@email.com"));
			repository.save(new Client("Chloe", "O'Brian","ChlObri@email.com"));
			repository.save(new Client("Kim", "Bauer","KimBav@email.com"));
			repository.save(new Client("David", "Palmer","DavPal@email.com"));
			repository.save(new Client("Michelle", "Dessler","MichDess@email.com"));
		};
	}

}
