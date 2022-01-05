package se.yrgo.employee.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.entities.enums.EmployeeStatus;
import se.yrgo.employee.entities.enums.SystemStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private EmployeeDTO dto;
    private static final long ID = 5000;

    @BeforeEach
    void setup() {
        dto = new EmployeeDTO(
                "Ana",
                "Beatriz",
                "890519-XXXX",
                "anna@gmail.com",
                "12345678",
                "Södra Vägen",
                "44556",
                "Göteborg",
                "developer",
                "volvo",
                LocalDate.of(2000, 1, 1),
                null,
                EmployeeStatus.VACATION,
                SystemStatus.SYSTEM_ADMIN
        );
    }

    @Test
    void createEmployeeFromDTOAndUserId() {
        final String userId = "anabea5678";
        Employee created = new Employee(dto, userId);
        assertEquals(userId, created.getUserId());
        assertEquals("Employee [id=null, userId=anabea5678, firstName=Ana, lastName=Beatriz, " +
                        "personalNumber=890519-XXXX, email=anna@gmail.com, phoneNumber=12345678, street=Södra Vägen, " +
                        "zip=44556, city=Göteborg, jobTitle=developer, parentCompany=volvo, startDate=2000-01-01, " +
                        "endDate=null, employeeStatus=3, systemStatus=1]"
                , created.toString());
    }

    @Test
    void createEmployeeFromDTOAndId() {
        Employee created = new Employee(dto, ID);
        assertEquals(ID, created.getId());
    }

    @Test
    void createEmployeeWhenStartingApplication() {
        final String email = dto.getEmail();
        Employee startUp = new Employee(
                ID,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPersonalNumber(),
                email,
                dto.getPhoneNumber(),
                dto.getStreet(),
                dto.getZip(),
                dto.getCity(),
                dto.getJobTitle(),
                dto.getParentCompany(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getEmployeeStatus(),
                dto.getSystemStatus()
        );
        assertEquals(ID, startUp.getId());
        assertEquals(email, startUp.getEmail());
    }
}
