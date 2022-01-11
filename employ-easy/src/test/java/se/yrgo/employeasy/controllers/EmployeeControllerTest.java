package se.yrgo.employeasy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import se.yrgo.employeasy.dto.EmployeeDTO;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;
import se.yrgo.employeasy.security.PasswordConfig;
import se.yrgo.employeasy.security.SecurityConfig;
import se.yrgo.employeasy.services.EmployeeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	private final List<EmployeeDTO> dtos = new ArrayList<>();
	
	@BeforeEach
	void setUp() {

		var mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
		
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(controller)
				.setMessageConverters(mappingJackson2HttpMessageConverter)
				.build();

		EmployeeDTO dto = new EmployeeDTO("Nadia", "Hamid", "900519-XXXX", "Nadia@gmail.com", "87654321", "Norra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.VACATION,
				SystemStatus.SYSTEM_ADMIN);
		dto.setUserId("nadham4321");
		dtos.add(dto);
		dto = new EmployeeDTO( "Marius", "Marthinussen", "881005-XXXX", "marius@gmail.com", "90654321", "Sodra Vagen",
				"44556", "Goteborg", "developer", "saab", LocalDate.of(2005, 1, 1), null, EmployeeStatus.ACTIVE,
				SystemStatus.USER);
		dto.setUserId("marmar1234");
		dtos.add(dto);
	}

	@Test
	void getAllEmployeesTest() throws Exception {
		when(service.findAll()).thenReturn(dtos);
		MvcResult mvcResult = mockMvc
			.perform(get(URL).with(user("admin").roles("ADMIN")))
			.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
			.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtos);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void registerEmployeeTest() throws Exception {
		var dto = dtos.get(0);
		when(service.addEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(dto);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post(URL).with(user("admin").roles("ADMIN"))
						.content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void deleteEmployeeTest() throws Exception {
		var dto = dtos.get(0);
		MvcResult mvcResult = mockMvc
				.perform(delete(URL + "/" + dto.getUserId()).with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andReturn();

		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	void editEmployeeTest() throws Exception {
		var dto = dtos.get(0);
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
		var dto = dtos.get(0);
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
		when(service.findByJobTitle(Mockito.any(String.class))).thenReturn(dtos);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "/jobtitle/developer")
						.with(user("admin").roles("ADMIN")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtos);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void findByUserIdTest() throws Exception {
		var dto = dtos.get(0);
		when(service.getByUserId(Mockito.any(String.class))).thenReturn(dto);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "/" + dto.getUserId())
						.with(user("admin").roles("ADMIN")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}
}
