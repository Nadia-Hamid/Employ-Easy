package se.yrgo.employeasy.vacation.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacation")
public class OpenDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate date;
    private String jobTitle;
    
    private static final int MULTIPLES = 1; 

    public OpenDate() {}

    public OpenDate(String jobTitle, LocalDate localDate) {
        this.jobTitle = jobTitle;
        this.date = localDate;
    }

    public LocalDate getDate() {
        return date;
    }
}
