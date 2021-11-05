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
				Employee emp = new Employee(1L, "Marius", "Marthinussen", "Marius@gmail.com", "12345678", LocalDate.of(2000, 1, 1));
				Employee emp2 = new Employee(2L, "Nadia", "Hamid", "Nadia@gmail.com", "12345678", LocalDate.of(2005, 1, 1));
				
				data.saveAll(List.of(emp, emp2));
			};
			
		}
	}

