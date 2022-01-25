package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

public class TableBookableDTO implements Serializable {
    private final Map<LocalDate, Long> vacationAvailable;

    public TableBookableDTO(Map<LocalDate, Long> vacationAvailable) {
        this.vacationAvailable = vacationAvailable;
    }

    public Map<LocalDate, Long> getVacationAvailable() {
        return Collections.unmodifiableMap(vacationAvailable);
    }
}
