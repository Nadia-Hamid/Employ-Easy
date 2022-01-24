package se.yrgo.employeasy.entities.enums;

/**
 * class SystemStatus
 * abstract Status codes describing the role of the employee. Use higher values when archiving.
 * updated 2022-01-20
 */
public enum SystemStatus {
    SYSTEM_ADMIN(1),
    USER(2);

    private final int code;

    SystemStatus(int code) {
        this.code = code;
    }

    /**
     @return System status integer representation.
     */
    public int getCode() {
        return code;
    }

    /**
     * @throws IllegalArgumentException when status code is invalid.
     * @param code System status integer representation.
     * @return SystemStatus type representation of integer.
     */
    public static SystemStatus valueOf(int code) {
        for (SystemStatus value : SystemStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid System Status code.");
    }
}
