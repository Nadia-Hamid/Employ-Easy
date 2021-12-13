package se.yrgo.employee.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.services.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	private EmployeeService mockedEmployeeService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getAllEmployees() throws Exception {

		List<EmployeeDTO> employeeDTOList = new ArrayList<>();

		List<Employee> employeeList = new ArrayList<>();

		Employee emp = new Employee("Marius", "Marthinussen", "890519-XXXX", "Marius@gmail.com", "12345678",
				"Sodra Vagen", "44556", "Goteborg", "developer", "volvo", LocalDate.of(2000, 1, 1), null);

		employeeList.add(emp);

		EmployeeDTO dto = new EmployeeDTO(emp);

		employeeDTOList.add(dto);

		emp = new Employee("Nadia", "Hamid", "900519-XXXX", "Nadia@gmail.com", "87654321", "Norra Vagen", "44556",
				"Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null);

		employeeList.add(emp);

		dto = new EmployeeDTO(emp);

		employeeDTOList.add(dto);

		when(mockedEmployeeService.findAll()).thenReturn(employeeList);

		String url = "/v1/employees";
		MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		String actualResponseJson = result.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(employeeDTOList);
		assertEquals(expectedResultJson, actualResponseJson);
		
	}