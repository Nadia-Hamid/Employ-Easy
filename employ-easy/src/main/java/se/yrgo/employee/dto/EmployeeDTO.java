package se.yrgo.employee.dto;

import java.io.Serializable;
import java.time.LocalDate;

import se.yrgo.employee.domain.Employee;

public class EmployeeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String employeeId;
	private String firstName;
	private String lastName;
	private String personalNumber;
	private String email;
	private String phoneNumber;
	private String street;
	private String zip;
	private String city;
	private String jobTitle;
	private String parentCompany;
	private LocalDate startDate;
	private LocalDate endDate;

	public EmployeeDTO() {
	}

	public EmployeeDTO(Employee object) {
		id = object.getId();
		employeeId = object.getEmployeeId();
		firstName = object.getfirstName();
		lastName = object.getLastName();
		personalNumber = object.getPersonalNumber();
		email = object.getEmail();
		phoneNumber = object.getPhoneNumber();
		street = object.getStreet();
		zip = object.getZip();
		city = object.getCity();
		jobTitle = object.getJobTitle();
		parentCompany = object.getParentCompany();
		startDate = object.getStartDate();
		endDate = object.getEndDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getParentCompany() {
		return parentCompany;
	}

	public void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
