package se.yrgo.employeasy.vacation.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

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
 
    public OpenDateDTO(LocalDate date) {
    	this.date = date; 
    }

//    public OpenDateDTO(VacationDate vacationDate) {
//        this.date = vacationDate.getDate();
//    }
    
    public OpenDateDTO(VacationDate vacationDate) {
    	this.userId = vacationDate.getUserId();
    	this.date = vacationDate.getDate();
    	this.jobTitle = vacationDate.getJobTitle();
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenDateDTO that = (OpenDateDTO) o;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
