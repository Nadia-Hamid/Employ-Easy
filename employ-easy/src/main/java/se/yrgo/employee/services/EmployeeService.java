package se.yrgo.employee.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.exceptions.ConflictException;
import se.yrgo.employee.exceptions.ObjectNotFoundException;
import se.yrgo.employee.repositories.EmployeeRepository;

/**
 *@class Service Class
 */

@Service
@Transactional
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;

	/**
	 *  Find all employees in the database.
	 * @return A DTO of all employees in the database
	 */
    public List<EmployeeDTO> findAll() {
    	
        return employeeRepository.findAll()
        		.stream()
        		.map(EmployeeDTO::new)
        		.collect(Collectors.toList());
    }

	/**
	 * Register a new Employee. 
	 * @return A DTO representational employee in the database.
	 * @param Representational entity of an Employee
	 */
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
    	
        final String newEmail = employeeDTO.getEmail();
        if(employeeRepository.findByEmail(newEmail).size() > 0){
            throw new ConflictException("Email " + newEmail + " was already added.");
        }
        String prefix = employeeDTO.generateName();
        while (true) {
            String userId = prefix + Employee.generateSuffix();
            Employee existing = employeeRepository.findEmployeeByUserId(userId);
            if (existing == null) {
                Employee employee = new Employee(employeeDTO, userId);
                employeeRepository.save(employee);
                return toDTO(employee);
            }
        }
    }

	/**
	 * Find an employee by it's user id.
	 * @return The entity employee in the database
	 * @param String of an user's id
	 */
    private Employee getEmployeeByUserId(String userId) {
    	
        Employee entity = employeeRepository.findEmployeeByUserId(userId.toLowerCase());
        if(entity == null){
            throw new ObjectNotFoundException("User was not found");
        }
        return entity;
    }

	/**
	 * Find an Employee entity by it's user id.
	 * @return A DTO employee in the database
	 * @param String of an user's id
	 */
    public EmployeeDTO getByUserId(String userId) {
    	
        return toDTO(getEmployeeByUserId(userId));
    }

	/**
	 * Find employees by their job title.
	 * @return A DTO List of employees in the database
	 * @param String with a job title
	 */
    public List<EmployeeDTO> findByJobTitle(String jobTitle) {
        return employeeRepository
                .findByJobTitle(jobTitle.toLowerCase())
                .stream()
                .map(EmployeeDTO::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of))
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new ObjectNotFoundException("No user with job title " + jobTitle + " was found"));
    }

    /**
	 * Update an employee in the database.
	 * @return A DTO employee in the database
	 * @param A DTO employee
	 */
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
    	
        Employee emp = getEmployeeByUserId(employeeDTO.getUserId());
        Employee updatedEmployee = new Employee(employeeDTO, emp.getId());
        Employee employee = employeeRepository.save(updatedEmployee);
        return toDTO(employee);
    }

    /**
	 * Delete an employee in the database.
	 * @param A String with the user's id to be deleted
	 */
    public void deleteEmployee(String userId) {
    	
        Employee employee = employeeRepository.findEmployeeByUserId(userId.toLowerCase());
        if(employee == null){
            throw new ObjectNotFoundException("User to be deleted was not found");
        }
        employeeRepository.delete(employee);
    }

    /**
	 * Find an employee by it's email.
	 * @return A DTO employee in the database
	 * @param A String with the user's email
	 */
	public EmployeeDTO findByEmail(String email) {

		var getEmployeeByEmail = employeeRepository.findByEmail(email.toLowerCase());
		var size = getEmployeeByEmail.size();
		if (size < 1) {
			throw new ObjectNotFoundException("No user with email " + email + " was found");
		} else if (size > 1) {
			throw new ConflictException("Several instances with email " + email + " were found");
		}
		return toDTO(getEmployeeByEmail.get(0));
	}
    
    /**
	 * Transform an Employee entity into a DTO object.
	 * @return A DTO employee in the database
	 * @param An Employee entity
	 */
    private EmployeeDTO toDTO(Employee employee){
    	
    	return new EmployeeDTO(employee);
    }
}
