package mygov.task01_B;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Task01BApplication {

	public static void main(String[] args) {
		SpringApplication.run(Task01BApplication.class, args);
	}

	@Bean
	CommandLineRunner seedCustomers(CustomerRepository customerRepository) {
		return args -> {
			customerRepository.save(new Customer(1L, "Faig", "faiq@idda.az", "(010) 389-80-81"));
			customerRepository.save(new Customer(2L, "Rauf", "rafu@idda.az", "(010) 123-45-67"));
			customerRepository.save(new Customer(3L, "Aysel", "aysel@idda.az", "(010) 345-67-89"));
		};
	}

}
