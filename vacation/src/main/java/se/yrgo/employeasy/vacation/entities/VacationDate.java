package se.yrgo.employeasy.vacation.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import se.yrgo.employeasy.vacation.dto.OpenDateDTO;

@Entity
@Table(name = "VACATIONDATE")
public class VacationDate implements Serializable{
	private static final long serialVersionUID = 1L;
	
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

    public VacationDate(String userId, LocalDate localDate, String jobTitle) {
		this.userId = userId;
		this.date = localDate;
		this.jobTitle = jobTitle;
	}

	public VacationDate(OpenDateDTO dto) {
		this.userId = dto.getUserId();
		this.date = dto.getDate();
		this.jobTitle = dto.getJobTitle();
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
