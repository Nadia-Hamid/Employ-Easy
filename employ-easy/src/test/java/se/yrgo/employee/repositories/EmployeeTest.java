package se.yrgo.employee.repositories;

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
        final long id = 5000;
        Employee created = new Employee(dto, id);
        assertEquals(id, created.getId());
    }
}