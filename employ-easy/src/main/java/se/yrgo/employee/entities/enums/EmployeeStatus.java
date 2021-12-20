package se.yrgo.employee.entities.enums;

public enum EmployeeStatus {
    ACTIVE(1),
    INACTIVE(2),
    VACATION(3),
    MATERNITY_LEAVE(4),
    OFF_DUTY(5),
    ARCHIVE(6);

    private int code;

    private EmployeeStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EmployeeStatus valueOf(int code) {
        for (EmployeeStatus value : EmployeeStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Employee Status code.");
    }
}
