package se.yrgo.employeasy.vacation.dto;

import se.yrgo.employeasy.vacation.entities.UserDate;

import java.io.Serializable;
import java.time.LocalDate;

public class OpenDateDTO implements Serializable {

    private LocalDate date;
    private static final int OPEN_DATES = 1;

    public OpenDateDTO(LocalDate date) { this.date = date; }

    public OpenDateDTO(UserDate userDate) {
        this.date = userDate.getDate();
    }

    public LocalDate getDate() {
        return date;
    }

    public int getOpenDates() { return OPEN_DATES; }
}
