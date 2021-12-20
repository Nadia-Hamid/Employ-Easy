package se.yrgo.employee.entities;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.enums.EmployeeStatus;
import se.yrgo.employee.entities.enums.SystemStatus;

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
    // private String imageURL;

    private Integer employeeStatus;
    private Integer systemStatus;

    public Employee() {}

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(EmployeeDTO employeeDTO, String userId) {
        this.userId = userId;
        this.firstName = employeeDTO.getFirstName();
        this.lastName = employeeDTO.getLastName();
        this.personalNumber = employeeDTO.getPersonalNumber();
        this.email = employeeDTO.getEmail().toLowerCase();
        this.phoneNumber = employeeDTO.getPhoneNumber();
        this.street = employeeDTO.getStreet();
        this.zip = employeeDTO.getZip();
        this.city = employeeDTO.getCity();
        this.jobTitle = employeeDTO.getJobTitle().toLowerCase();
        this.parentCompany = employeeDTO.getParentCompany().toLowerCase();
        this.startDate = employeeDTO.getStartDate();
        this.endDate = employeeDTO.getEndDate();
        // this.imageURL = imageURL;
        setEmployeeStatus(employeeDTO.getEmployeeStatus());
        setSystemStatus(employeeDTO.getSystemStatus());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        this.jobTitle = jobTitle.toLowerCase();
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany.toLowerCase();
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

    /*
     * public String getImageURL() { return imageURL; }
     *
     * public void setImageURL(String imageURL) { this.imageURL = imageURL; }
     */

    public EmployeeStatus getEmployeeStatus() {
        return EmployeeStatus.valueOf(employeeStatus);
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        if (employeeStatus != null) {
            this.employeeStatus = employeeStatus.getCode();
        }
    }

    public SystemStatus getSystemStatus() {
        return SystemStatus.valueOf(systemStatus);
    }

    public void setSystemStatus(SystemStatus systemStatus) {
        if (systemStatus != null) {
            this.systemStatus = systemStatus.getCode();
        }
    }

    @Override
    public String toString() {
        return (
            "Employee [id=" +
            id +
            ", userId=" +
            userId +
            ", firstName=" +
            firstName +
            ", lastName=" +
            lastName +
            ", personalNumber=" +
            personalNumber +
            ", email=" +
            email +
            ", phoneNumber=" +
            phoneNumber +
            ", street=" +
            street +
            ", zip=" +
            zip +
            ", city=" +
            city +
            ", jobTitle=" +
            jobTitle +
            ", parentCompany=" +
            parentCompany +
            ", startDate=" +
            startDate +
            ", endDate=" +
            endDate +
            ", employeeStatus=" +
            employeeStatus +
            ", systemStatus=" +
            systemStatus +
            "]"
        );
    }

    //only for tests
    // , String imageURL
    public Employee(
        String firstName,
        String lastName,
        String personalNumber,
        String email,
        String phoneNumber,
        String street,
        String zip,
        String city,
        String jobTitle,
        String parentCompany,
        LocalDate startDate,
        LocalDate endDate,
        EmployeeStatus employeeStatus,
        SystemStatus systemStatus
    ) {
        this.userId = generateUserId(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.email = email.toLowerCase();
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.jobTitle = jobTitle.toLowerCase();
        this.parentCompany = parentCompany.toLowerCase();
        this.startDate = startDate;
        this.endDate = endDate;
        // this.imageURL = imageURL;
        setEmployeeStatus(employeeStatus);
        setSystemStatus(systemStatus);
    }

    public static String generateSuffix() {
        return String.format("%04d", ThreadLocalRandom.current().nextInt(0, 9999 + 1));
    }

    //only for tests
    private String generateUserId(String firstName, String lastName) {
        String userId = firstName.substring(0, 3) + lastName.substring(0, 3) + generateSuffix();
        return userId.toLowerCase();
    }

    //only for Employee Config file
    public Employee(
            int id,
            String firstName,
            String lastName,
            String personalNumber,
            String email,
            String phoneNumber,
            String street,
            String zip,
            String city,
            String jobTitle,
            String parentCompany,
            LocalDate startDate,
            LocalDate endDate,
            EmployeeStatus employeeStatus,
            SystemStatus systemStatus
    ) {
        String userId = firstName.substring(0, 3) + lastName.substring(0, 3) + id;
        this.userId = userId.toLowerCase();
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.email = email.toLowerCase();
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.jobTitle = jobTitle.toLowerCase();
        this.parentCompany = parentCompany.toLowerCase();
        this.startDate = startDate;
        this.endDate = endDate;
        // this.imageURL = imageURL;
        setEmployeeStatus(employeeStatus);
        setSystemStatus(systemStatus);
    }
}
