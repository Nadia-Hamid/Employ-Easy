package se.yrgo.employee.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import se.yrgo.employee.domain.Employee;
import se.yrgo.employee.services.EmployeeService;

@RestController
@RequestMapping(value = "/v1/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public List<Employee> getEmployees() {
		return employeeService.findAll();
	}

	@PostMapping
	public void registerEmployee(@RequestBody Employee employee) {
		employeeService.addEmployee(employee);
	}

	@GetMapping(value = "/{id}")
	public Optional<Employee> findById(@PathVariable Long id) {
		Optional<Employee> employee = employeeService.findById(id);
		return employee;
	}

	@GetMapping(value = "/jobTitle/{jobTitle}")
	public List<Employee> findByJobTitle(@PathVariable String jobTitle) {
		List<Employee> employees = employeeService.findByJobTitle(jobTitle);
		return employees;
	}
	
	@RequestMapping(value = "/addemployee", method = RequestMethod.POST)
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		employeeService.save(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}
}
