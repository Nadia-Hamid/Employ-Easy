package se.yrgo.employee.configuration;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.yrgo.employee.domain.Employee;
import se.yrgo.employee.repositories.EmployeeRepository;

@Configuration
public class EmployeeConfig {

	@Bean
	CommandLineRunner commandLineRunner(EmployeeRepository data) {
		return args -> {
			Employee emp = new Employee(1L, "Marius", "Marthinussen", "890519-XXXX", "Marius@gmail.com",
					"12345678", "Södra Vägen", "44556", "Göteborg", "developer", "volvo", LocalDate.of(2000, 1, 1));
			Employee emp2 = new Employee("Nadia", "Hamid", "900519-XXXX", "Naida@gmail.com", "87654321",
					"Norra Vägen", "44556", "Göteborg", "developer", "saab", LocalDate.of(2005, 1, 1));

			data.saveAll(List.of(emp, emp2));
		};

	}
}