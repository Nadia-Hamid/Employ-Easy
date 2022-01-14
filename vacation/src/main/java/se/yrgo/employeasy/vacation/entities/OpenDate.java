package se.yrgo.employeasy.vacation.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacation")
public class OpenDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate date = LocalDate.now();
    private String jobTitle;

    public OpenDate() {}

    public OpenDate(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getDate() {
        return date;
    }
}
