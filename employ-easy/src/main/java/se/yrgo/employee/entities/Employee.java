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
    private int employeeStatus;
    private int systemStatus;
    // private String imageURL;

    public Employee() {}

    private Employee(
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
        setEmployeeStatus(employeeStatus);
        setSystemStatus(systemStatus);
        // this.imageURL = imageURL;
    }

    public Employee(EmployeeDTO employeeDTO, String userId) {
        this(
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getPersonalNumber(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getStreet(),
                employeeDTO.getZip(),
                employeeDTO.getCity(),
                employeeDTO.getJobTitle(),
                employeeDTO.getParentCompany(),
                employeeDTO.getStartDate(),
                employeeDTO.getEndDate(),
                employeeDTO.getEmployeeStatus(),
                employeeDTO.getSystemStatus()
        );
        this.userId = userId;
    }

    public Employee(EmployeeDTO employeeDTO, Long id) {
        this(
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getPersonalNumber(),
                employeeDTO.getEmail(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getStreet(),
                employeeDTO.getZip(),
                employeeDTO.getCity(),
                employeeDTO.getJobTitle(),
                employeeDTO.getParentCompany(),
                employeeDTO.getStartDate(),
                employeeDTO.getEndDate(),
                employeeDTO.getEmployeeStatus(),
                employeeDTO.getSystemStatus()
        );
        this.userId = employeeDTO.getUserId();
        this.id = id;
    }

    //only for unit tests and Employee Startup file
    public Employee(
            long id,
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
        this(
                firstName,
                lastName,
                personalNumber,
                email,
                phoneNumber,
                street,
                zip,
                city,
                jobTitle,
                parentCompany,
                startDate,
                endDate,
                employeeStatus,
                systemStatus
        );
        if(id < 0) {
            this.userId = generateUserId(firstName, lastName);
        } else {
            this.userId = firstName.substring(0, 3) + lastName.substring(0, 3) + id;
            this.id = id;
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

    public static String generateSuffix() {
        return String.format("%04d", ThreadLocalRandom.current().nextInt(0, 9999 + 1));
    }

    //only for tests
    private String generateUserId(String firstName, String lastName) {
        String userId = firstName.substring(0, 3) + lastName.substring(0, 3) + generateSuffix();
        return userId.toLowerCase();
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

    public void setStreet(String street) { this.street = street; }

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

    /*
     * public String getImageURL() { return imageURL; }
     *
     * public void setImageURL(String imageURL) { this.imageURL = imageURL; }
     */
}
