package se.yrgo.employeasy.vacation.controllers;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.exceptions.JobTitleNotFoundException;
import se.yrgo.employeasy.vacation.services.VacationService;

@ComponentScan(basePackages = "se.yrgo.employeasy.vacation.controllers")
@WebMvcTest(VacationController.class)
public class VacationControllerTest {

	@MockBean
	private VacationService service;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getAllAsDeveloper() throws Exception {

		final List<OpenDateDTO> dtos = new ArrayList<>();

		dtos.add(new OpenDateDTO(new VacationDate("developer", LocalDate.of(2022, 6, 20))));
		dtos.add(new OpenDateDTO(new VacationDate("developer", LocalDate.of(2022, 6, 21))));
		dtos.add(new OpenDateDTO(new VacationDate("developer", LocalDate.of(2022, 6, 22))));

		when(service.getAllFromJobTitle("developer")).thenReturn(dtos);

		MvcResult mvcResult = mockMvc.perform(get("/v1/vacations/developer"))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtos);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void getAllAsNonExistent() throws Exception {
		final String jobTitle = "nonexistent";
		when(service.getAllFromJobTitle(jobTitle))
				.thenThrow(new JobTitleNotFoundException("No open dates with job title " + jobTitle + " was found."));
		mockMvc.perform(get("/v1/vacations/" + jobTitle)).andExpect(status().isNotFound()).andReturn();
	}
}
