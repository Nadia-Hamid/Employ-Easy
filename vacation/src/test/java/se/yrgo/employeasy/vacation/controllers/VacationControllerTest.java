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
import se.yrgo.employeasy.vacation.exceptions.DoubleBookedException;
import se.yrgo.employeasy.vacation.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.vacation.exceptions.TimeException;
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
	private static final String LOG_DATE = "The date as a String: ";
	private static final String URL = "/v1/vacations/";
	private static final String JOB_TITLE = "developer";
	private static final String USER_ID = "marmar1234";
	private static final int FUTURE = LocalDate.now().getYear() + 1;
	private static final LocalDate MID_SUMMER = LocalDate.of(FUTURE, 6, 20);

	@Test
	void getAvailableDatesAsDeveloperSuccessfully() throws Exception {

		final Set<OpenDateDTO> dtos = new HashSet<>();

		dtos.add(new OpenDateDTO(MID_SUMMER));
		dtos.add(new OpenDateDTO(MID_SUMMER.plusDays(1)));
		dtos.add(new OpenDateDTO(MID_SUMMER.plusDays(2)));

		when(service.getAllFromJobTitle(JOB_TITLE)).thenReturn(dtos);

		MvcResult mvcResult = mockMvc.perform(get(URL + JOB_TITLE))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dtos);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void getAvailableDatesAsNonExistentShouldThrowNotFound() throws Exception {
		final String nonExistent = "nonexistent";
		when(service.getAllFromJobTitle(nonExistent))
				.thenThrow(new ObjectNotFoundException("No open dates with job title " + nonExistent + " was found."));
		MvcResult mvcResult = this.mockMvc
				.perform(get(URL + nonExistent)).andExpect(status().isNotFound()).andReturn();
		assertEquals("{\"error\":\"Not Found\"}", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void reserveVacationFutureDateAsUser() throws Exception {
		ReservedDateDTO dto = new ReservedDateDTO(MID_SUMMER, USER_ID);

		LOGGER.info(LOG_DATE + MID_SUMMER);
		when(service.requestReservationUsingJobTitle(MID_SUMMER, USER_ID, JOB_TITLE)).thenReturn(dto);

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.put(URL + JOB_TITLE)
						.content(objectMapper.writeValueAsString(dto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void reserveVacationPastDueDateAsUserShouldThrowTimeException() throws Exception {
		LocalDate oldDate = LocalDate.of(2021, 12, 24);
		ReservedDateDTO dto = new ReservedDateDTO(oldDate, USER_ID);

		LOGGER.info(LOG_DATE + oldDate);
		when(service.requestReservationUsingJobTitle(oldDate, USER_ID, JOB_TITLE))
				.thenThrow(new TimeException("Vacation date " + oldDate + " needs to be in the future."));
		MvcResult mvcResult = this.mockMvc
				.perform(put(URL + JOB_TITLE).content(objectMapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();

		assertEquals("{\"error\":\"Old vacation date\"}", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void reserveVacationNonExistentFutureDateShouldThrowNotFound() throws Exception {
		LocalDate futureWorkDay = LocalDate.of(FUTURE, 3, 28);
		ReservedDateDTO dto = new ReservedDateDTO(futureWorkDay, USER_ID);

		LOGGER.info(LOG_DATE + futureWorkDay);
		when(service.requestReservationUsingJobTitle(futureWorkDay, USER_ID, JOB_TITLE))
				.thenThrow(new ObjectNotFoundException("No open dates with date " + futureWorkDay + " was found."));
		MvcResult mvcResult = this.mockMvc
				.perform(put(URL + JOB_TITLE).content(objectMapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
		assertEquals("{\"error\":\"Not Found\"}", mvcResult.getResponse().getContentAsString());
	}

	@Test
	void doubleBookingVacationAsUserShouldNotBeAllowed() throws Exception  {
		ReservedDateDTO dto = new ReservedDateDTO(MID_SUMMER, USER_ID);

		LOGGER.info(LOG_DATE + MID_SUMMER);
		when(service.requestReservationUsingJobTitle(MID_SUMMER, USER_ID, JOB_TITLE))
				.thenThrow(new DoubleBookedException("A single user can only book a date once"));

		this.mockMvc.perform(MockMvcRequestBuilders.put(URL + JOB_TITLE)
						.content(objectMapper.writeValueAsString(dto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()))
				.andReturn();
	}

}
