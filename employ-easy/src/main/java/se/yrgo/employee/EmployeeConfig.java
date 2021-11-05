package se.yrgo.employee;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmployeeConfig {
	
		@Bean
		CommandLineRunner commandLineRunner(EmployeeRepository data) {
			return args -> {
				Employee emp = new Employee(1L, "marmar1234", "Marius", "Marthinussen", "890519-XXXX","Marius@gmail.com", "12345678","Södra Vägen", "44556", "Göteborg", "developer", "volvo", LocalDate.of(2000, 1, 1));
				Employee emp2 = new Employee("nadham4321", "Nadia", "Hamid", "900519-XXXX","Naida@gmail.com", "87654321","Norra Vägen", "44556", "Göteborg", "developer", "saab", LocalDate.of(2005, 1, 1));
				
				data.saveAll(List.of(emp, emp2));
			};
			
		}
	}

