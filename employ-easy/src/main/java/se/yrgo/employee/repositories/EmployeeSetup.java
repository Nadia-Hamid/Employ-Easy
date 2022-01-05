package se.yrgo.employee.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.entities.enums.EmployeeStatus;
import se.yrgo.employee.entities.enums.SystemStatus;

import java.time.LocalDate;
import java.util.List;

@Component
public class EmployeeSetup implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public EmployeeSetup(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        employeeRepository.saveAll(List.of(
                new Employee(1234,
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
                        4321, "Nadia",
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
