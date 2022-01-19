package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;

import se.yrgo.employeasy.vacation.entities.VacationDate;

public class OpenDateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String userId;
    private LocalDate date;
    private String jobTitle;
    
    public OpenDateDTO() {}
        
    public OpenDateDTO(String userId, LocalDate date, String jobTitle) {
    	this.userId = userId;
    	this.date = date;
    	this.jobTitle = jobTitle;
    }
    
    public OpenDateDTO(VacationDate openDate) {
    	this.userId = openDate.getUserId();
    	this.date = openDate.getDate();
    	this.jobTitle = openDate.getJobTitle();
    }
    
    public LocalDate getDate() {
        return date;
    }

	public String getUserId() {
		return userId;
	}

	public String getJobTitle() {
		return jobTitle;
	}
}
