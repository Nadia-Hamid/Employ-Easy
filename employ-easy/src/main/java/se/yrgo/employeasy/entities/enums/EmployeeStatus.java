package se.yrgo.employeasy.entities.enums;

/**
 * class EmployeeStatus
 * abstract Status codes describing the employee. Archive deleted when audit is needed.
 * updated 2022-01-20
 */
public enum EmployeeStatus {
    ACTIVE(1),
    INACTIVE(2),
    VACATION(3),
    MATERNITY_LEAVE(4),
    OFF_DUTY(5),
    ARCHIVE(6);

    private final int code;

    EmployeeStatus(int code) {
        this.code = code;
    }

    /**
    @return Employee status integer representation.
     */
    public int getCode() {
        return code;
    }

    /**
     * @throws IllegalArgumentException when status code is invalid.
     * @param code Employee status integer representation.
     * @return EmployeeStatus type representation of integer.
     */
    public static EmployeeStatus valueOf(int code) {
        for (EmployeeStatus value : EmployeeStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Employee Status code.");
    }
}
