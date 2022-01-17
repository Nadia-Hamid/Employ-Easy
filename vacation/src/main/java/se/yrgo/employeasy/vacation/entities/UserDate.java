package se.yrgo.employeasy.vacation.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "USERDATE")
public class UserDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userId;
    private String jobTitle;
    private LocalDate date;

    public UserDate(String userId, String jobTitle, LocalDate date) {
        this.userId = userId;
        this.jobTitle = jobTitle;
        this.date = date;
    }

    public UserDate() {}

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public LocalDate getDate() {
        return date;
    }
}
