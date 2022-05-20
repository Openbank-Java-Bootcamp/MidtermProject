package com.ironhack.midtermproject;

import com.ironhack.midtermproject.model.DTO.CheckingDTO;
import com.ironhack.midtermproject.model.DTO.CreditCardDTO;
import com.ironhack.midtermproject.model.DTO.SavingsDTO;
import com.ironhack.midtermproject.model.DTO.ThirdPartyUserDTO;
import com.ironhack.midtermproject.model.Role;
import com.ironhack.midtermproject.model.accounts.Account;
import com.ironhack.midtermproject.model.accounts.Money;
import com.ironhack.midtermproject.model.accounts.Savings;
import com.ironhack.midtermproject.model.accounts.StudentChecking;
import com.ironhack.midtermproject.model.users.AccountHolder;
import com.ironhack.midtermproject.model.users.Address;
import com.ironhack.midtermproject.model.users.Admin;
import com.ironhack.midtermproject.service.impl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@SpringBootApplication
public class MidtermProjectApplication {

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService, CheckingService checkingService, ThirdPartyUserService thirdPartyUserService, SavingsService savingsService, CreditCardService creditCardService, StudentCheckingService studentCheckingService) {
		return args -> {

			//creating roles
			userService.saveRole(new Role( "ROLE_HOLDER"));
			userService.saveRole(new Role( "ROLE_ADMIN"));

			//creating admin
			userService.saveUser(new Admin("Laura", "1234", "Laura"));

			//creating AccountHolder(Age +24) without mailing address
			Address address = new Address("Liepu", "Klaipeda", "125");
			LocalDate date = LocalDate.of(1993, 5, 12);
			userService.saveUser(new AccountHolder("Edita", "1234", "Edita", date, address));
			Address address4 = new Address("Nemuno", "Siauliai", "30");
			LocalDate date4 = LocalDate.of(1990, 5, 12);
			userService.saveUser(new AccountHolder("Jordi", "1234", "Jordi", date4, address4));


			//creating AccountHolder(Age <24)
			Address address1 = new Address("Kanguru", "Vilnius", "10");
			Address mailingAddress = new Address("Liepu", "Silute", "1");
			LocalDate date1 = LocalDate.of(2000, 5, 12);
			userService.saveUser(new AccountHolder("Lina", "1234", "Lina", date1, address1, mailingAddress));

			//creating AccountHolder(Age <18)
			Address address2 = new Address("Liepu", "Kaunas", "10");
			LocalDate date2 = LocalDate.of(2015, 5, 12);
			userService.saveUser(new AccountHolder("Lucas", "1234", "Lucas", date2, address2));

			//adding roles
			userService.addRoleToUser("Laura", "ROLE_ADMIN");
			userService.addRoleToUser("Edita", "ROLE_HOLDER");
			userService.addRoleToUser("Lina", "ROLE_HOLDER");
			userService.addRoleToUser("Lucas", "ROLE_HOLDER");
			userService.addRoleToUser("Jordi", "ROLE_HOLDER");

			//creating Checking accounts
			checkingService.saveChecking(new CheckingDTO(new Money(new BigDecimal(1500)),"Jess",2));
			checkingService.saveChecking(new CheckingDTO(new Money(new BigDecimal(400)),"Jim",2));
			checkingService.saveChecking(new CheckingDTO(new Money(new BigDecimal(400)),"Jordi",5));

			//creating Student account
			studentCheckingService.saveStudentChecking(new CheckingDTO(new Money(new BigDecimal(350)),"James",3));

			//creating a third party user
			thirdPartyUserService.storeThirdPartyUser(new ThirdPartyUserDTO("Jordi"));
			thirdPartyUserService.storeThirdPartyUser(new ThirdPartyUserDTO("Josh"));
			thirdPartyUserService.storeThirdPartyUser(new ThirdPartyUserDTO("Caty"));

			//creating a Savings account
			savingsService.storeSavings(new SavingsDTO(new Money(new BigDecimal(1500)), "James", new Money(new BigDecimal(300)), new Money(new BigDecimal(0.3)), 2));

			//creating
			creditCardService.storeCreditCard(new CreditCardDTO(new Money(new BigDecimal(1500)), "James", "Jess", 3, new Money(new BigDecimal(300)), new BigDecimal(0.15), LocalDate.now()));


		};
	}

	public static void main(String[] args) {
		SpringApplication.run(MidtermProjectApplication.class, args);
	}
}
