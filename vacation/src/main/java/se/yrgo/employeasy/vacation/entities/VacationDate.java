package se.yrgo.employeasy.vacation.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VacationDate that = (VacationDate) o;

        if (userId == null) {
            return new EqualsBuilder()
                    .append(id, that.id).append(jobTitle, that.jobTitle).append(date, that.date)
                    .isEquals();
        } else {
            return new EqualsBuilder()
                    .append(jobTitle, that.jobTitle).append(date, that.date).append(userId, that.userId)
                    .isEquals();
        }
    }

    @Override
    public int hashCode() {
        if (userId == null) {
            return new HashCodeBuilder(17, 37)
                    .append(id).append(jobTitle).append(date)
                    .toHashCode();
        } else {
            return new HashCodeBuilder(17, 37)
                    .append(jobTitle).append(date).append(userId)
                    .toHashCode();
        }
    }
}
