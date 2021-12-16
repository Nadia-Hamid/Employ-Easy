package se.yrgo.employee.entities.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SystemStatusTest {

    @Test
    void valueOfThrowsIllegalArg() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> SystemStatus.valueOf(-1));
        assertEquals("Invalid System Status code.", thrown.getMessage());
    }

    @Test
    void valueOfReturnsCorrectly() {
        assertEquals(SystemStatus.SYSTEM_ADMIN, SystemStatus.valueOf(1));
    }
}
