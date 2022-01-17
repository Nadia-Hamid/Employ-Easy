package se.yrgo.employeasy.repositories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.yrgo.employeasy.entities.Employee;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;

import java.time.LocalDate;
import java.util.List;

@Component
public class EmployeeStartup implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public EmployeeStartup(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        employeeRepository.saveAll(List.of(
                new Employee(1234L,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                "Marius@gmail.com",
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
                ), new Employee(
                        4321L,
                        "Nadia",
                        "Hamid",
                        "900519-XXXX",
                        "Marius@gmail.com", //Nadia
                        "87654321",
                        "Norra Vägen",
                        "44556",
                        "Göteborg",
                        "developer",
                        "saab",
                        LocalDate.of(2005, 1, 1),
                        null,
                        EmployeeStatus.VACATION,
                        SystemStatus.SYSTEM_ADMIN
                ))
        );
    }
}
