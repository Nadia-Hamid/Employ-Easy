package se.yrgo.employeasy.services;

import se.yrgo.employeasy.dto.EmployeeDTO;

import java.util.List;

/**
 * class EmployeeService
 * abstract Service klass with use cases connecting controller with databases.
 * updated 2022-01-19
 */
public interface EmployeeService {

    /**
     *  Find all employees in the database.
     * @return A DTO of all employees in the database
     */
    List<EmployeeDTO> findAll();

    /**
     * Register a new Employee.
     * @return A DTO representational employee in the database.
     * @param employeeDTO Representational entity of an Employee
     */
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    /**
     * Find an Employee entity by its user id.
     * @return A DTO employee in the database
     * @param userId String of an user's id
     */
    EmployeeDTO getByUserId(String userId);

    /**
     * Update an employee in the database.
     * @return A DTO representation of the employee in the database
     * @param employeeDTO Data that was requested to be updated.
     */
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    /**
     * Delete an employee in the database.
     * @param userId A String with the user's id to be deleted
     */
    void deleteEmployee(String userId);

    /**
     * Find an employee by its email.
     * @return A DTO representation of the employee in the database
     * @param email A String with the user's email
     */
    EmployeeDTO findByEmail(String email);

    /**
     * Find employees by their job title.
     * @return A DTO List of employees in the database
     * @param jobTitle String with a job title
     */
    List<EmployeeDTO> findByJobTitle(String jobTitle);
}
