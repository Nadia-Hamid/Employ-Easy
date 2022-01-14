package se.yrgo.employeasy.vacation.dto;

import se.yrgo.employeasy.vacation.entities.OpenDate;

import java.io.Serializable;
import java.time.LocalDate;

public class OpenDateDTO implements Serializable {

    private LocalDate date;

    public OpenDateDTO(OpenDate openDate) {
        this.date = openDate.getDate();
    }

    public LocalDate getDate() {
        return date;
    }
}
