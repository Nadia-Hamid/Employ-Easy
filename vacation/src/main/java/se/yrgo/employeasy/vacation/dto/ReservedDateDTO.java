package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ReservedDateDTO implements Serializable {

    private LocalDate date;
    private String userId;

    public ReservedDateDTO(LocalDate date, String userId) {
        this.date = date;
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUserId() { return userId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservedDateDTO that = (ReservedDateDTO) o;
        return date.equals(that.date) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, userId);
    }
}
