package se.yrgo.employeasy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employeasy.dto.EmployeeDTO;
import se.yrgo.employeasy.entities.Employee;
import se.yrgo.employeasy.exceptions.ConflictException;
import se.yrgo.employeasy.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * class EmployeeService
 * abstract Service klass with use cases connecting controller with databases.
 * updated 2022-01-30
 */
@Service("employeeService")
@Transactional
public class EmployeeServiceTransactional implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeServiceTransactional(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public List<EmployeeDTO> findAll() {
    return employeeRepository.findAll().stream().map(EmployeeDTO::new).sorted().collect(Collectors.toList());
  }

  private EmployeeDTO dto(Employee employee){
    return new EmployeeDTO(employee);
  }

  /**
   * Register a new Employee.
   * @return A DTO representational employee in the database.
   * @param employeeDTO Representational entity of an Employee
   */
  public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
    final String newEmail = employeeDTO.getEmail();
    if(employeeRepository.findByEmail(newEmail).size() > 0){
      throw new ConflictException("Employee with unique email " + newEmail + " was already added.");
    }
    String prefix = employeeDTO.generateName();
    String userId;
    Employee existing;
    do {
      userId = prefix + Employee.generateSuffix();
      existing = employeeRepository.findEmployeeByUserId(userId);
    } while(existing != null);
    Employee employee = new Employee(employeeDTO, userId);
    employeeRepository.save(employee);
    return dto(employee);
  }

  /**
   * Find an Employee by its user id.
   * @return The entity employee in the database
   * @param userId String of an user's id
   */
  Employee getEmployeeByUserId(String userId) {
    Employee entity = employeeRepository.findEmployeeByUserId(userId.toLowerCase());
    if(entity == null){
      throw new ObjectNotFoundException("User was not found");
    }
    return entity;
  }

  /**
   * Find an Employee entity by its user id.
   * @return A DTO employee in the database
   * @param userId String of an user's id
   */
  public EmployeeDTO getByUserId(String userId) {
    return dto(getEmployeeByUserId(userId));
  }

  /**
   * Update an employee in the database.
   * @return A DTO representation of the employee in the database
   * @param employeeDTO Data that was requested to be updated.
   */
  public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
    Employee emp = getEmployeeByUserId(employeeDTO.getUserId());
    Employee updatedEmployee = new Employee(employeeDTO, emp.getId());
    Employee employee = employeeRepository.save(updatedEmployee);
    return dto(employee);
  }

  /**
   * Delete an employee in the database.
   * @param userId A String with the user's id to be deleted
   */
  public void deleteEmployee(String userId) {
    Employee employee = getEmployeeByUserId(userId);
    employeeRepository.delete(employee);
  }

  /**
   * Find an employee by its email.
   * @return A DTO representation of the employee in the database
   * @param email A String with the user's email
   */
  public EmployeeDTO findByEmail(String email) {
    if(email == null) {
      throw new NullPointerException("Null email value not allowed!");
    }
    String lowerCaseEmail = email.toLowerCase();
    var getEmployeeByEmail = employeeRepository.findByEmail(lowerCaseEmail);
    var size = getEmployeeByEmail.size();
    if(size < 1){
      throw new ObjectNotFoundException("No user with email " + lowerCaseEmail + " was found");
    } else if(size > 1){
      StringBuilder sb = new StringBuilder("Several instances with email ");
      sb.append(lowerCaseEmail);
      sb.append(" was found. User ids: ");
      for (Employee employee : getEmployeeByEmail) {
        sb.append(employee.getUserId());
        sb.append(", ");
      }
      throw new ConflictException(sb.toString());
    }
    return dto(getEmployeeByEmail.get(0));
  }

  /**
   * Find employees by their job title.
   * @return A DTO List of employees in the database
   * @param jobTitle String with a job title
   */
  public List<EmployeeDTO> findByJobTitle(String jobTitle) {
    return employeeRepository
      .findByJobTitle(jobTitle.toLowerCase())
      .stream()
      .map(EmployeeDTO::new)
      .sorted()
      .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of))
      .filter(l -> !l.isEmpty())
      .orElseThrow(() -> new ObjectNotFoundException("No user with job title " + jobTitle + " was found"));
  }
}