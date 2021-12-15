package se.yrgo.employee.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.services.EmployeeService;

@RestController
@RequestMapping(value = "/v1/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		List<EmployeeDTO> listDTO = employeeService.findAll();
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<EmployeeDTO> getByUserId(@PathVariable String userId) {
		EmployeeDTO theOne = employeeService.getByUserId(userId);
		return ResponseEntity.ok().body(theOne);
	}

	@RequestMapping(value = "/jobtitle/{jobTitle}", method = RequestMethod.GET)
	public ResponseEntity<List<EmployeeDTO>> findByJobTitle(@PathVariable String jobTitle) {
		List<Employee> list = employeeService.findByJobTitle(jobTitle);
		List<EmployeeDTO> listDTO = list.stream().map(EmployeeDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody EmployeeDTO registerDTO) {
		Employee entity = employeeService.addEmployee(registerDTO);
		return ResponseEntity.ok().body(new EmployeeDTO(entity));
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable String userId) {
		employeeService.deleteEmployee(userId);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee updateEmployee = employeeService.updateEmployee(employeeDTO);
        return new ResponseEntity<EmployeeDTO>(new EmployeeDTO(updateEmployee), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<EmployeeDTO> findEqualEmail(@PathVariable String email) {
		Employee entity = employeeService.findByEmail(email);
		return ResponseEntity.ok().body(new EmployeeDTO(entity));
	}
}
