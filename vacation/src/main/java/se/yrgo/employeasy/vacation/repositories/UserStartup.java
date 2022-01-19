package se.yrgo.employeasy.vacation.repositories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.yrgo.employeasy.vacation.entities.VacationDate;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserStartup implements CommandLineRunner {

    private final DateRepository dateRepository;

    public UserStartup(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    @Override
    public void run(String... args) {
        dateRepository.saveAll(List.of(
            new VacationDate("developer", LocalDate.of(2022, 6, 20)),
                new VacationDate("developer", LocalDate.of(2022, 6, 21)),
                new VacationDate("developer", LocalDate.of(2022, 6, 22))
        ));
    }
}
