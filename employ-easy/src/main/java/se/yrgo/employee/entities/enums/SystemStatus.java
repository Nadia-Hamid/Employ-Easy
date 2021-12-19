package se.yrgo.employee.entities.enums;

public enum SystemStatus {
    SYSTEM_ADMIN(1),
    USER(2);

    private int code;

    private SystemStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SystemStatus valueOf(int code) {
        for (SystemStatus value : SystemStatus.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid System Status code.");
    }
}
