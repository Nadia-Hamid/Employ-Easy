package se.yrgo.employee.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.yrgo.employee.security.PasswordConfig;
import se.yrgo.employee.security.SecurityConfig;
import se.yrgo.employee.services.EmployeeService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {SecurityConfig.class, PasswordConfig.class})
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	private EmployeeService mockedEmployeeService;

	@Autowired
	private MockMvc mockMvc;

	private static final String URL = "/v1/employees";

	@Test
	void getAllEmployees() throws Exception {
		when(mockedEmployeeService.findAll()).thenReturn(new ArrayList<>());
		MvcResult result = mockMvc.perform(get(URL).with(user("admin").roles("ADMIN"))).andExpect(status().isNotFound()).andReturn();
		String actualResponseJson = result.getResponse().getContentAsString();
		assertEquals("", actualResponseJson);
	}

	@Test
	void deleteEmployee() throws Exception {
		final String user = "marher1234";
		MvcResult result = mockMvc.perform(delete(URL + "/" + user).with(user("admin").roles("ADMIN")))
				.andExpect(status().isNotFound()).andReturn();
		String actualResponseJson = result.getResponse().getContentAsString();
		assertEquals("", actualResponseJson);
	}
}