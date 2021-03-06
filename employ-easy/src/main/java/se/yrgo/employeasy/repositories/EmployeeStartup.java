package se.yrgo.employeasy.repositories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.yrgo.employeasy.entities.Employee;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * class EmployeeStartup
 * abstract Database initializer for employee domain.
 * updated 2022-01-20
 */
@Component
public class EmployeeStartup implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public EmployeeStartup(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Employees added during start up
     * @param args Main arguments during start up.
     */
    @Override
    public void run(String... args) {
        employeeRepository.saveAll(List.of(
                new Employee(1234L,
                "Marius",
                "Marthinussen",
                "890519-XXXX",
                "marius@gmail.com",
                "072-234 51 24",
                "Södra Vägen 1",
                "445 56",
                "Göteborg",
                "Developer",
                "Volvo",
                LocalDate.of(2015, 1, 1),
                null,
                EmployeeStatus.ACTIVE,
                SystemStatus.USER
                ), new Employee(
                        4321L,
                        "Nadia",
                        "Hamid",
                        "900519-XXXX",
                        "nadia@gmail.com", //Nadia
                        "074-599 93 33",
                        "Norra Vägen 13",
                        "445 56",
                        "Göteborg",
                        "Developer",
                        "Saab",
                        LocalDate.of(2015, 1, 1),
                        null,
                        EmployeeStatus.VACATION,
                        SystemStatus.SYSTEM_ADMIN
                ), new Employee(
                        1235L,
                        "Mårten",
                        "Hernebring",
                        "860519-XXXX",
                        "marten@gmail.com",
                        "077-26 64 876",
                        "Västra Frölunda 10",
                        "421 47",
                        "Göteborg",
                        "CEO",
                        "Volvo",
                        LocalDate.of(2009, 9, 1),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.SYSTEM_ADMIN
                ), new Employee(1236L,
                        "Aiste",
                        "Pakstyte",
                        "900719-XXXX",
                        "aiste@gmail.com",
                        "072-267 71 66",
                        "Södra Hamngatan 3",
                        "411 06",
                        "Göteborg",
                        "Developer",
                        "Ericsson",
                        LocalDate.of(2017, 8, 15),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ), new Employee(1237L,
                        "Anna",
                        "Andersson",
                        "850801-XXXX",
                        "anna@gmail.com",
                        "072-377 76 66",
                        "Drottninggatan 3",
                        "411 07",
                        "Göteborg",
                        "UX Designer",
                        "Ericsson",
                        LocalDate.of(2010, 2, 15),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ), new Employee(1238L,
                        "Benjamin",
                        "Bengtsson",
                        "800109-XXXX",
                        "benji@gmail.com",
                        "079-111 43 43",
                        "Karl Johansgatan 10",
                        "414 55",
                        "Göteborg",
                        "Chief Architect",
                        "Sigma",
                        LocalDate.of(2010, 12, 15),
                        null,
                        EmployeeStatus.VACATION,
                        SystemStatus.USER
                ), new Employee(1239L,
                        "John",
                        "Johnsson",
                        "751214-XXXX",
                        "johny@gmail.com",
                        "071-555 89 10",
                        "Gårdavägen 6",
                        "412 50",
                        "Göteborg",
                        "Senior Developer",
                        "Volvo Cars",
                        LocalDate.of(2008, 10, 15),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ), new Employee(1240L,
                        "Henry",
                        "Ekberg",
                        "980113-XXXX",
                        "henry@gmail.com",
                        "072-897 11 11",
                        "Mellangatan 1",
                        "413 01",
                        "Göteborg",
                        "Junior Developer",
                        "Unit4",
                        LocalDate.of(2020, 5, 20),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ), new Employee(1241L,
                        "Sandra",
                        "Ryan",
                        "900221-XXXX",
                        "sandra@gmail.com",
                        "073-453 12 12",
                        "Burgårdsgatan 10",
                        "412 52",
                        "Göteborg",
                        "Web Developer",
                        "Bluetest AB",
                        LocalDate.of(2016, 6, 13),
                        null,
                        EmployeeStatus.VACATION,
                        SystemStatus.USER
                ), new Employee(1242L,
                        "Liam",
                        "Smith",
                        "840325-XXXX",
                        "liam@gmail.com",
                        "073-453 12 12",
                        "Pilgatan 45",
                        "413 01",
                        "Göteborg",
                        "Product Manager",
                        "ESAB",
                        LocalDate.of(2014, 9, 15),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ), new Employee(1243L,
                        "Don",
                        "Donaldson",
                        "700501-XXXX",
                        "don@gmail.com",
                        "077-998 33 55",
                        "Lärlingsgatan 4",
                        "415 06",
                        "Göteborg",
                        "QA",
                        "Acobia",
                        LocalDate.of(2005, 11, 15),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ), new Employee(1244L,
                        "Evelina",
                        "Miller",
                        "910709-XXXX",
                        "evelina@gmail.com",
                        "074-321 55 87",
                        "Prinsgatan 56",
                        "413 05",
                        "Göteborg",
                        "Developer",
                        "Sigma",
                        LocalDate.of(2015, 12, 15),
                        null,
                        EmployeeStatus.INACTIVE,
                        SystemStatus.USER
                ),new Employee(1246L,
                        "Gregory",
                        "Myers",
                        "800312-XXXX",
                        "greg@gmail.com",
                        "077-098 23 53",
                        "Nilsgatan 17",
                        "411 03",
                        "Göteborg",
                        "Designer",
                        "Volvo",
                        LocalDate.of(2010, 9, 15),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ), new Employee(1245L,
                        "Nick",
                        "Jones",
                        "721108-XXXX",
                        "nick@gmail.com",
                        "072-155 65 44",
                        "Ryavägen 4",
                        "418 34",
                        "Göteborg",
                        "Software Engineer",
                        "Astecon AB",
                        LocalDate.of(2000, 10, 15),
                        null,
                        EmployeeStatus.ACTIVE,
                        SystemStatus.USER
                ))
        );
    }
}
