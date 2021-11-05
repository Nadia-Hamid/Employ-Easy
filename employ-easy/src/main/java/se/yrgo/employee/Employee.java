package se.yrgo.employee;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Employee {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	private String firtName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate startDate;

	public Employee() {

	}

	public Employee(Long id, String firtName, String lastNameString, String email, String phoneNumber, LocalDate startDate) {
		this.id = id;
		this.firtName = firtName;
		this.lastName = lastNameString;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.startDate = startDate;
	}

	public Employee(String firtName, String lastNameString, String email, String phoneNumber, LocalDate startDate) {
		this.firtName = firtName;
		this.lastName = lastNameString;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.startDate = startDate;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirtName() {
		return firtName;
	}

	public void setFirtName(String firtName) {
		this.firtName = firtName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firtName=" + firtName + ", lastNameString=" + lastName + ", email="
				+ email + ", phoneNumber=" + phoneNumber + ", startDate=" + startDate + "]";
	}

}
