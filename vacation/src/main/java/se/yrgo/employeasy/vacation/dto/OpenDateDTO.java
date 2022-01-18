package se.yrgo.employeasy.vacation.dto;

import se.yrgo.employeasy.vacation.entities.VacationDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class OpenDateDTO implements Serializable {

    private LocalDate date;

    public OpenDateDTO(LocalDate date) { this.date = date; }

    public OpenDateDTO(VacationDate vacationDate) {
        this.date = vacationDate.getDate();
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenDateDTO that = (OpenDateDTO) o;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
