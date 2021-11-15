package se.yrgo.employee.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.repositories.EmployeeRepository;
import se.yrgo.employee.services.exceptions.ResourceNotFoundException;

@Service
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
		return object.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public List<Employee> findByJobTitle(String jobTitle) {
		List<Employee> employees = employeeRepository.findByJobTitle(jobTitle);
		return employees;
	}

	public Employee updateEmployee(Long id, Employee object) {
		Employee entity = employeeRepository.getById(id);
		updateData(entity, object);
		return employeeRepository.save(entity);
	}

	public void deleteEmployee(Long id) {
		try {
			employeeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public Employee findByEmail(String email) {
		Employee entity = employeeRepository.findByMail(email);
		return entity;
	}

	private void updateData(Employee entity, Employee object) {
		//TODO
	}

	public Employee fromDTO(EmployeeDTO dto) {
		return new Employee(dto.getUserId(), dto.getFirstName(), dto.getLastName(), dto.getPersonalNumber(),
				dto.getEmail(), dto.getPhoneNumber(), dto.getStreet(), dto.getZip(), dto.getCity(), dto.getJobTitle(),
				dto.getParentCompany(), dto.getStartDate(), dto.getEndDate());
		//, dto.getImageURL()
	}

	public Employee generateUserId(EmployeeDTO dto) {
		return new Employee(dto.getFirstName(), dto.getLastName(), dto.getPersonalNumber(),
				dto.getEmail(), dto.getPhoneNumber(), dto.getStreet(), dto.getZip(), dto.getCity(), dto.getJobTitle(),
				dto.getParentCompany(), dto.getStartDate(), dto.getEndDate());
		//, dto.getImageURL()
	}
}
