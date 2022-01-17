package se.yrgo.employeasy.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
