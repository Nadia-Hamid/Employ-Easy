package se.yrgo.employee.entities;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userId;
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
	//private String imageURL;

	public Employee() {
	}

	//, String imageURL
	public Employee(String userId, String firstName, String lastName, String personalNumber, String email,
			String phoneNumber, String street, String zip, String city, String jobTitle, String parentCompany,
			LocalDate startDate, LocalDate endDate) {
		this.userId = userId;
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
		//this.imageURL = imageURL;
	}

	//, String imageURL
	public Employee(String firstName, String lastName, String personalNumber, String email, String phoneNumber, String street, String zip, String city, String jobTitle, String parentCompany, LocalDate startDate, LocalDate endDate) {
		this.userId = generateUserId(firstName, lastName);
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
		//this.imageURL = imageURL;
	}

	public String generateUserId(String firstName, String lastName) {

		StringBuilder sb = new StringBuilder();
		sb.append(firstName.substring(0, 3));
		sb.append(lastName.substring(0, 3));
		sb.append(ThreadLocalRandom.current().nextInt(0, 9999 + 1));
		String userId = sb.toString();
		return userId.toLowerCase();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	/*public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}*/

	@Override
	public String toString() {
		return "Employee [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", personalNumber=" + personalNumber + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", street=" + street + ", zip=" + zip + ", city=" + city + ", jobTitle=" + jobTitle
				+ ", parentCompany=" + parentCompany + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
