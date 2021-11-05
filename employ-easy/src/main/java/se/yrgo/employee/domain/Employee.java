package se.yrgo.employee.domain;

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

	public Employee() {
	}

	public Employee(Long id, String employeeId, String firstName, String lastName, String personalNumber, String email,
			String phoneNumber, String street, String zip, String city, String jobTitle, String parentCompany,
			LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.personalNumber = personalNumber;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.jobTitle = jobTitle;
		this.parentCompany = parentCompany;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Employee(Long id, String employeeId, String firstName, String lastName, String personalNumber, String email,
			String phoneNumber, String street, String zip, String city, String jobTitle, String parentCompany,
			LocalDate startDate) {
		this.id = id;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.personalNumber = personalNumber;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.jobTitle = jobTitle;
		this.parentCompany = parentCompany;
		this.startDate = startDate;
	}

	public Employee(String employeeId, String firstName, String lastName, String personalNumber, String email,
			String phoneNumber, String street, String zip, String city, String jobTitle, String parentCompany,
			LocalDate startDate) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.personalNumber = personalNumber;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.jobTitle = jobTitle;
		this.parentCompany = parentCompany;
		this.startDate = startDate;
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

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeId=" + employeeId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", personalNumber=" + personalNumber + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", street=" + street + ", zip=" + zip + ", city=" + city + ", jobTitle=" + jobTitle
				+ ", parentCompany=" + parentCompany + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
