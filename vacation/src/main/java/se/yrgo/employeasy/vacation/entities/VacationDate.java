package se.yrgo.employeasy.vacation.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "VACATIONDATE")
public class VacationDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String jobTitle;
    private LocalDate date;
    private String userId;

    public VacationDate(String jobTitle, LocalDate date) {
        this.jobTitle = jobTitle;
        this.date = date;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public VacationDate() {}

    public long getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }
}
