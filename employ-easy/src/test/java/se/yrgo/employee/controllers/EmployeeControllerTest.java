package se.yrgo.employee.controllers;

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

import org.junit.jupiter.api.AfterEach;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.entities.enums.EmployeeStatus;
import se.yrgo.employee.entities.enums.SystemStatus;
import se.yrgo.employee.repositories.EmployeeRepository;
import se.yrgo.employee.security.PasswordConfig;
import se.yrgo.employee.security.SecurityConfig;
import se.yrgo.employee.services.EmployeeService;

@ComponentScan(basePackages = "se.yrgo.employee.controllers")
@ContextConfiguration(classes = { SecurityConfig.class, PasswordConfig.class })
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	private EmployeeRepository repo;

	@MockBean
	private EmployeeService service;

	@Autowired
	private EmployeeController controller;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String URL = "/v1/employees/";
	
	private final String email = "new@email.com";

	private Employee emp;
	private Employee emp2;
	
	@BeforeEach
	void setUp() {
		
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setMessageConverters(mappingJackson2HttpMessageConverter)
				.build();
		
		emp = new Employee("Nadia", "Hamid", "900519-XXXX", "nadia@gmail.com", "87654321", "Norra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.VACATION,
				SystemStatus.SYSTEM_ADMIN);
		
		emp2 = new Employee("Marius", "Marthinussen", "881005-XXXX", "marius@gmail.com", "90654321", "Sadra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.ACTIVE,
				SystemStatus.USER);
	}
	
	@AfterEach
	public void tearDown() {
		service.deleteEmployee(emp.getUserId());
		service.deleteEmployee(emp2.getUserId());
	}

	@Test
	void getAllEmployeesTest() throws Exception {

		List<Employee> list = Arrays.asList(emp, emp2);
		List<EmployeeDTO> dtoList = list.stream().map(EmployeeDTO::new).collect(Collectors.toList());

		when(service.findAll()).thenReturn(dtoList);

		MvcResult mvcResult = mockMvc
				.perform(get(URL)
				.with(user("admin").roles("ADMIN")))
				.andExpect(MockMvcResultMatchers.status()
						.is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtoList);

		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void registerEmployeeTest() throws Exception {

		Employee newEmp = new Employee("Suzanna", "Jones", "900519-XXXX", "sus@gmail.com", "87654321", "Norra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.VACATION,
				SystemStatus.SYSTEM_ADMIN);
		
		EmployeeDTO dto = new EmployeeDTO(newEmp);

		when(service.addEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(dto);

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders
						.post(URL)
						.with(user("admin").roles("ADMIN"))
						.content(objectMapper.writeValueAsString(newEmp))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status()
						.is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedresultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedresultJson, actualResponseJson);
		System.out.println(actualResponseJson);
		System.out.println(expectedresultJson);
	}

	@Test
	void deleteEmployeeTest() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(delete(URL + "/" + emp.getUserId())
						.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	void editEmployeeTest() throws Exception {

		EmployeeDTO dto = new EmployeeDTO(emp);
		dto.setEmail(email);
		emp.setEmail(email);

		when(repo.findEmployeeByUserId(Mockito.any(String.class))).thenReturn(emp);
		when(service.updateEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(dto);

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders
						.put(URL)
						.with(user("admin").roles("ADMIN"))
						.content(objectMapper.writeValueAsString(emp))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status()
						.is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResponseJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResponseJson, actualResponseJson);
	}
	
	@Test
	void findEqualEmailTest() throws JsonProcessingException, Exception {
		
		EmployeeDTO dto = new EmployeeDTO(emp);
		
		when(service.findByEmail(Mockito.any(String.class))).thenReturn(dto);
		
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders
						.get(URL + "/email/nadia@gmail.com")
						.with(user("admin")
						.roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status()
						.is(HttpStatus.OK.value()))
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
				.perform(MockMvcRequestBuilders
						.get(URL + "/jobtitle/developer")
						.with(user("admin").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status()
						.is(HttpStatus.OK.value())).andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedresultJson = objectMapper.writeValueAsString(dtoList);
		assertEquals(expectedresultJson, actualResponseJson);
	}

	@Test
	void findByUserIdTest() throws Exception {
		
		EmployeeDTO dto = new EmployeeDTO(emp);
				
		when(service.getByUserId(Mockito.any(String.class))).thenReturn(dto);
		
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders
						.get(URL + "/" + emp.getUserId())
						.with(user("admin").roles("ADMIN"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status()
						.is(HttpStatus.OK.value())).andReturn();
		
		
		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedresultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedresultJson, actualResponseJson);
	}
}
