package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class OpenDateDTO implements Serializable {
    private LocalDate date = LocalDate.now();

    public LocalDate getDate() {
        return date;
    }
}
