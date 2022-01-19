package se.yrgo.employeasy.dto;

import org.junit.jupiter.api.Test;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GenerateUserNameTest {

    @Test
    void generateNameShouldOnlyContainEnglishCharacters() {
        EmployeeDTO dto = new EmployeeDTO(
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
        );
        assertEquals("marher", dto.generateName());
    }
}