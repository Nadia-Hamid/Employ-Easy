package se.yrgo.employee.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.repositories.EmployeeRepository;
import se.yrgo.employee.exceptions.ObjectNotFoundException;

@Service
@Transactional
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findById(Long id) {
		Optional<Employee> object = employeeRepository.findById(id);
		return object.orElseThrow(() -> new ObjectNotFoundException("Object not found."));
	}

	public List<Employee> findByJobTitle(String jobTitle) {
		List<Employee> employees = employeeRepository.findByJobTitle(jobTitle);
		return employees;
	}

	public Employee updateEmployee(Employee employee) {
		try {
		Employee updatedEmployee = employeeRepository.findEmployeeByUserId(employee.getUserId());
		updatedEmployee.setId(updatedEmployee.getId());
		updatedEmployee.setUserId(employee.getUserId());
		updatedEmployee.setCity(employee.getCity());
		updatedEmployee.setEmail(employee.getEmail());
		updatedEmployee.setEndDate(employee.getEndDate());
		updatedEmployee.setfirstName(employee.getfirstName());
		updatedEmployee.setJobTitle(employee.getJobTitle());
		updatedEmployee.setLastName(employee.getLastName());
		updatedEmployee.setPersonalNumber(employee.getPersonalNumber());
		updatedEmployee.setPhoneNumber(employee.getPhoneNumber());
		updatedEmployee.setParentCompany(employee.getParentCompany());
		updatedEmployee.setStartDate(employee.getStartDate());
		updatedEmployee.setStreet(employee.getStreet());
		updatedEmployee.setZip(employee.getZip());
		return employeeRepository.save(updatedEmployee);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Employee not found");
		}
	}

	public void deleteEmployee(String userId) {
		try {
			Employee employee = employeeRepository.findEmployeeByUserId(userId);
			employeeRepository.delete(employee);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Object not found");
		}
	}

	public Employee findByEmail(String email) {
		Employee entity = employeeRepository.findByMail(email);
		return entity;
	}

	public Employee fromDTO(EmployeeDTO dto) {
		return new Employee(dto.getUserId(), dto.getFirstName(), dto.getLastName(), dto.getPersonalNumber(),
				dto.getEmail(), dto.getPhoneNumber(), dto.getStreet(), dto.getZip(), dto.getCity(), dto.getJobTitle(),
				dto.getParentCompany(), dto.getStartDate(), dto.getEndDate());
		// , dto.getImageURL()
	}

	public Employee generateUserId(EmployeeDTO dto) {
		return new Employee(dto.getFirstName(), dto.getLastName(), dto.getPersonalNumber(), dto.getEmail(),
				dto.getPhoneNumber(), dto.getStreet(), dto.getZip(), dto.getCity(), dto.getJobTitle(),
				dto.getParentCompany(), dto.getStartDate(), dto.getEndDate(), null, null);
		// , dto.getImageURL()
	}
}