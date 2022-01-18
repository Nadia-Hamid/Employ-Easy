package se.yrgo.employeasy.vacation.dto;

import se.yrgo.employeasy.vacation.entities.VacationDate;

import java.io.Serializable;
import java.time.LocalDate;

public class OpenDateDTO implements Serializable {

    private LocalDate date;

    public OpenDateDTO(LocalDate date) { this.date = date; }

    public OpenDateDTO(VacationDate vacationDate) {
        this.date = vacationDate.getDate();
    }

    public LocalDate getDate() {
        return date;
    }
}
