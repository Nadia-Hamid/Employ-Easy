package se.yrgo.employee.entities.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeStatusTest {

    @Test
    void valueOfThrowsIllegalArg() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> EmployeeStatus.valueOf(-1)
        );
        assertEquals("Invalid Employee Status code.", thrown.getMessage());
    }

    @Test
    void valueOfReturnsCorrectly() {
        assertEquals(EmployeeStatus.ACTIVE, EmployeeStatus.valueOf(1));
    }
}
