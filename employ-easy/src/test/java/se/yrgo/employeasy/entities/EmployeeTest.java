package se.yrgo.employeasy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.yrgo.employeasy.dto.EmployeeDTO;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private EmployeeDTO dto;
    private static final long ID = 5000;
    private static final String USER_ID = "anabea5678";

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
        Employee created = new Employee(dto, USER_ID);
        assertEquals(USER_ID, created.getUserId());
        assertEquals("Employee [id=0, userId=anabea5678, firstName=Ana, lastName=Beatriz, " +
                        "personalNumber=890519-XXXX, email=anna@gmail.com, phoneNumber=12345678, street=Södra Vägen, " +
                        "zip=44556, city=Göteborg, jobTitle=developer, parentCompany=volvo, startDate=2000-01-01, " +
                        "endDate=null, employeeStatus=3, systemStatus=1]"
                , created.toString());
    }

    @Test
    void createEmployeeFromDTOAndId() {
        dto.setUserId(USER_ID); //avoid null pointer exc
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

    static Stream<String> badStrings() {
        return Stream.of(USER_ID.substring(1), USER_ID + "?", null);
    }

    @ParameterizedTest
    @MethodSource("badStrings")
    void userIdMustHaveLength10(final String testValue) {
        assertTrue(testValue == null || testValue.length() != USER_ID.length());
        assertThrows(UnsupportedOperationException.class, () -> new Employee(dto, testValue));
    }
}
