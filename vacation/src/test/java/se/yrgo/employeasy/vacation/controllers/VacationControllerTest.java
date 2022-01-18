package se.yrgo.employeasy.vacation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.dto.ReservedDateDTO;
import se.yrgo.employeasy.vacation.exceptions.JobTitleNotFoundException;
import se.yrgo.employeasy.vacation.services.VacationService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "se.yrgo.employeasy.vacation.controllers")
@WebMvcTest(VacationController.class)
public class VacationControllerTest {

	@MockBean
	private VacationService service;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(VacationControllerTest.class);

	private static final String URL = "/v1/vacations/";

	@Test
	void getAvailableDatesAsDeveloperSuccessfully() throws Exception {

		final Set<OpenDateDTO> dtos = new HashSet<>();

		dtos.add(new OpenDateDTO(LocalDate.of(2022, 6, 20)));
		dtos.add(new OpenDateDTO(LocalDate.of(2022, 6, 21)));
		dtos.add(new OpenDateDTO(LocalDate.of(2022, 6, 22)));

		final String jobTitle = "developer";

		when(service.getAllFromJobTitle(jobTitle)).thenReturn(dtos);

		MvcResult mvcResult = mockMvc.perform(get(URL + jobTitle))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtos);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void getAvailableDatesAsDeveloperWasNonExistent() throws Exception {
		final String jobTitle = "nonexistent";
		when(service.getAllFromJobTitle(jobTitle))
				.thenThrow(new JobTitleNotFoundException("No open dates with job title " + jobTitle + " was found."));
		mockMvc.perform(get(URL + jobTitle)).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	void reserveVacationDateAsUser() throws Exception {
		LocalDate requestedDate = LocalDate.of(2022, 6, 20);
		String userId = "marmar1234";
		ReservedDateDTO dto = new ReservedDateDTO(requestedDate, userId);
		String jobTitle = "developer";

		LOGGER.info("The date as a String: " + requestedDate);
		when(service.requestReservationUsingJobTitle(requestedDate, userId, jobTitle)).thenReturn(dto);

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.put(URL + jobTitle)
						.content(objectMapper.writeValueAsString(dto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}

}
