package se.yrgo.employeasy.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.yrgo.employeasy.dto.EmployeeDTO;
import se.yrgo.employeasy.entities.Employee;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;
import se.yrgo.employeasy.security.PasswordConfig;
import se.yrgo.employeasy.security.SecurityConfig;
import se.yrgo.employeasy.services.EmployeeService;

@ComponentScan(basePackages = "se.yrgo.employeasy.controllers")
@ContextConfiguration(classes = { SecurityConfig.class, PasswordConfig.class })
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	private EmployeeService service;

	@Autowired
	private EmployeeController controller;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String URL = "/v1/employees/";

	private Employee emp;
	private Employee emp2;
	
	@BeforeEach
	void setUp() {

		var mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
		
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setMessageConverters(mappingJackson2HttpMessageConverter)
				.build();

		emp = new Employee(-1L, "Nadia", "Hamid", "900519-XXXX", "Nadia@gmail.com", "87654321", "Norra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.VACATION,
				SystemStatus.SYSTEM_ADMIN);

		emp2 = new Employee(-1L, "Marius", "Marthinussen", "881005-XXXX", "marius@gmail.com", "90654321", "Sadra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.ACTIVE,
				SystemStatus.USER);
	}

	/*
			List<EmployeeDTO> employeeDTOList = new ArrayList<>();
		Employee emp = new Employee(-1L, "Nadia", "Hamid", "900519-XXXX", "Nadia@gmail.com", "87654321", "Norra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.VACATION,
				SystemStatus.SYSTEM_ADMIN);
		EmployeeDTO dto = new EmployeeDTO(emp);
		employeeDTOList.add(dto);
		emp = new Employee(-1L, "Marius", "Marthinussen", "881005-XXXX", "marius@gmail.com", "90654321", "Sadra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.ACTIVE,
				SystemStatus.USER);
		dto = new EmployeeDTO(emp);
		employeeDTOList.add(dto);
	 */

	@Test
	void getAllEmployeesTest() throws Exception {
		List<Employee> list = Arrays.asList(emp, emp2);
		List<EmployeeDTO> dtoList = list.stream().map(EmployeeDTO::new).collect(Collectors.toList());
		when(service.findAll()).thenReturn(dtoList);
		MvcResult mvcResult = mockMvc
			.perform(get(URL).with(user("admin").roles("ADMIN")))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
			.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtoList);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void registerEmployeeTest() throws Exception {
		Employee newEmp = new Employee(-1, "Suzanna", "Jones", "900519-XXXX", "sus@gmail.com", "87654321", "Norra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.VACATION,
				SystemStatus.SYSTEM_ADMIN);
		EmployeeDTO dto = new EmployeeDTO(newEmp);
		when(service.addEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(dto);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post(URL).with(user("admin").roles("ADMIN"))
						.content(objectMapper.writeValueAsString(newEmp)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void deleteEmployeeTest() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(delete(URL + "/" + emp.getUserId()).with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andReturn();

		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void editEmployeeTest() throws Exception {
		EmployeeDTO dto = new EmployeeDTO(emp);
		final String email = "new@email.com";
		dto.setEmail(email);
		when(service.updateEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(dto);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.put(URL).with(user("admin").roles("ADMIN"))
						.content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResponseJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResponseJson, actualResponseJson);
	}
	
	@Test
	void findEqualEmailTest() throws Exception {
		EmployeeDTO dto = new EmployeeDTO(emp);
		when(service.findByEmail(Mockito.any(String.class))).thenReturn(dto);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "/email/nadia@gmail.com")
						.with(user("admin").roles("ADMIN")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();
		
		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResponseJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResponseJson, actualResponseJson);
	}

	@Test
	void findByJobTitleTest() throws Exception {
		List<Employee> list = Arrays.asList(emp, emp2);
		List<EmployeeDTO> dtoList = list.stream().map(EmployeeDTO::new).collect(Collectors.toList());
		when(service.findByJobTitle(Mockito.any(String.class))).thenReturn(dtoList);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "/jobtitle/developer")
						.with(user("admin").roles("ADMIN")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtoList);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void findByUserIdTest() throws Exception {
		EmployeeDTO dto = new EmployeeDTO(emp);
		when(service.getByUserId(Mockito.any(String.class))).thenReturn(dto);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "/" + emp.getUserId())
						.with(user("admin").roles("ADMIN")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}

}
