package se.yrgo.employee.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<EmployeeDTO> findAll() {
		return employeeRepository.findAll().stream().map(EmployeeDTO::new).collect(Collectors.toList());
	}

	public Employee addEmployee(EmployeeDTO employeeDTO) {
		String prefix = employeeDTO.generateName();
		while(true) {
			StringBuilder sb = new StringBuilder(prefix);
			sb.append(ThreadLocalRandom.current().nextInt(0, 9999 + 1));
			String userId = sb.toString();
			Employee existing = employeeRepository.findEmployeeByUserId(userId);
			if(existing == null) {
				Employee employee = new Employee(employeeDTO, userId);
				employeeRepository.save(employee);
				return employee;
			}
		}
	}

	public EmployeeDTO getByUserId(String userId) {
		Employee entity = employeeRepository.findEmployeeByUserId(userId);
		return new EmployeeDTO(entity);
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
		updatedEmployee.setFirstName(employeeDTO.getFirstName());
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
}