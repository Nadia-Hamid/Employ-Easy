package se.yrgo.employeasy.vacation.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import se.yrgo.employeasy.vacation.entities.VacationDate;


@Component
public class VacationStartup implements CommandLineRunner {

	private final VacationRepository vacationRepository;

	public VacationStartup(VacationRepository vacationRepository) {
		this.vacationRepository = vacationRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		vacationRepository.saveAll(List.of(new VacationDate("developer", LocalDate.of(2022, 6, 20)),
				new VacationDate("developer", LocalDate.of(2022, 6, 21)),
				new VacationDate("developer", LocalDate.of(2022, 6, 22))));
	}
}
