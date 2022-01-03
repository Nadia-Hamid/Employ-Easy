package se.yrgo.employee.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import se.yrgo.employee.entities.enums.EmployeeStatus;
import se.yrgo.employee.entities.enums.SystemStatus;

class EnumStatusTest {

    @Test
    void employeeValueOfThrowsIllegalArg() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> EmployeeStatus.valueOf(-1)
        );
        assertEquals("Invalid Employee Status code.", thrown.getMessage());
    }

    @Test
    void employeeValueOfReturnsCorrectly() {
        assertEquals(EmployeeStatus.ACTIVE, EmployeeStatus.valueOf(1));
    }

    @Test
    void systemValueOfThrowsIllegalArg() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> SystemStatus.valueOf(-1));
        assertEquals("Invalid System Status code.", thrown.getMessage());
    }

    @Test
    void systemValueOfReturnsCorrectly() {
        assertEquals(SystemStatus.SYSTEM_ADMIN, SystemStatus.valueOf(1));
    }
}
