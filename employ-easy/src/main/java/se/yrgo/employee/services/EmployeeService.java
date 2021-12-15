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

	public Employee updateEmployee(EmployeeDTO employeeDTO) {
		try {
		Employee updatedEmployee = employeeRepository.findEmployeeByUserId(employeeDTO.getUserId());
		updatedEmployee.setId(updatedEmployee.getId());
		updatedEmployee.setUserId(employeeDTO.getUserId());
		updatedEmployee.setCity(employeeDTO.getCity());
		updatedEmployee.setEmail(employeeDTO.getEmail());
		updatedEmployee.setEndDate(employeeDTO.getEndDate());
		updatedEmployee.setfirstName(employeeDTO.getFirstName());
		updatedEmployee.setJobTitle(employeeDTO.getJobTitle());
		updatedEmployee.setLastName(employeeDTO.getLastName());
		updatedEmployee.setPersonalNumber(employeeDTO.getPersonalNumber());
		updatedEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
		updatedEmployee.setParentCompany(employeeDTO.getParentCompany());
		updatedEmployee.setStartDate(employeeDTO.getStartDate());
		updatedEmployee.setStreet(employeeDTO.getStreet());
		updatedEmployee.setZip(employeeDTO.getZip());
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