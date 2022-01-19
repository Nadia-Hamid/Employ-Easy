package se.yrgo.employeasy.vacation.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import se.yrgo.employeasy.vacation.dto.OpenDateDTO;

@Entity
//@Table(name = "vacation")
public class VacationDate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private LocalDate date;
	private String jobTitle;
	private String userId;

	private static final int MULTIPLES = 1;

	public VacationDate() {
	}

	public VacationDate(String jobTitle, LocalDate localDate) {
		this.jobTitle = jobTitle;
		this.date = localDate;
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

	public String getUserId() {
		return userId;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getJobTitle() {
		return jobTitle;
	}
}
