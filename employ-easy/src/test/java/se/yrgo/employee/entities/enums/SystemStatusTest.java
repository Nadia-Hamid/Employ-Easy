package se.yrgo.employee.entities.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemStatusTest {

    @Test
    void valueOfThrowsIllegalArg() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> SystemStatus.valueOf(-1));
        assertEquals("Invalid System Status code.", thrown.getMessage());
    }

    @Test
    void valueOfReturnsCorrectly() {
        assertEquals(SystemStatus.SYSTEM_ADMIN, SystemStatus.valueOf(1));
    }
}