package se.yrgo.employee.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.repositories.EmployeeRepository;
import se.yrgo.employee.services.exceptions.ObjectNotFoundException;

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

<<<<<<< HEAD
	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);
=======
	public Employee addEmployee(Employee employee) {
		Employee entity = employeeRepository.findByMail(employee.getEmail());
//		if (entity.getEmail() == employee.getEmail()) {
//			throw new IllegalArgumentException("Email already existent.");
//		}
		return employeeRepository.save(employee);
>>>>>>> 8f56ba3 (Got enums & exceptions)
	}

	public Employee findById(Long id) {
		Optional<Employee> object = employeeRepository.findById(id);
		return object.orElseThrow(() -> new ObjectNotFoundException("Object not found."));
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
			employeeRepository.deleteById(id);
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
