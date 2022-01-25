package se.yrgo.employeasy.vacation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.yrgo.employeasy.vacation.dto.*;
import se.yrgo.employeasy.vacation.exceptions.DoubleBookedException;
import se.yrgo.employeasy.vacation.exceptions.ObjectNotFoundException;
import se.yrgo.employeasy.vacation.exceptions.TimeException;
import se.yrgo.employeasy.vacation.services.VacationService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
	private static final int CURRENT = LocalDate.now().getYear();
	private static final LocalDate MID_SUMMER = LocalDate.of(CURRENT, 6, 20);

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
		assertEquals("{\"status\":404,\"error\":\"Not Found\"," +
				"\"message\":\"No open dates with job title nonexistent was found.\"}",
				mvcResult.getResponse().getContentAsString());
	}

	@Test
	void reserveVacationFutureDateAsUser() throws Exception {
		ReservedDateDTO dto = new ReservedDateDTO(MID_SUMMER, USER_ID);

		LOGGER.info(LOG_DATE + MID_SUMMER);
		when(service.requestReservationUsingJobTitle(dto, JOB_TITLE)).thenReturn(dto);

		String expectedResultJson = objectMapper.writeValueAsString(dto);
		String actualResponseJson = this.mockMvc
				.perform(MockMvcRequestBuilders.put(URL + JOB_TITLE)
						.content(expectedResultJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void reserveVacationPastDueDateAsUserShouldThrowTimeException() throws Exception {
		LocalDate oldDate = LocalDate.of(2021, 12, 24);
		ReservedDateDTO dto = new ReservedDateDTO(oldDate, USER_ID);

		LOGGER.info(LOG_DATE + oldDate);
		when(service.requestReservationUsingJobTitle(dto, JOB_TITLE))
				.thenThrow(new TimeException("Vacation date " + oldDate + " needs to be in the future."));
		MvcResult mvcResult = this.mockMvc
				.perform(put(URL + JOB_TITLE).content(objectMapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();

		assertEquals("{\"status\":400,\"error\":\"Old vacation date\"," +
				"\"message\":\"Vacation date 2021-12-24 needs to be in the future.\"}",
				mvcResult.getResponse().getContentAsString());
	}

	@Test
	void reserveVacationNonExistentFutureDateShouldThrowNotFound() throws Exception {
		LocalDate futureWorkDay = LocalDate.of(CURRENT, 3, 28);
		ReservedDateDTO dto = new ReservedDateDTO(futureWorkDay, USER_ID);

		LOGGER.info(LOG_DATE + futureWorkDay);
		when(service.requestReservationUsingJobTitle(dto, JOB_TITLE))
				.thenThrow(new ObjectNotFoundException("No open dates with date " + futureWorkDay + " was found."));
		MvcResult mvcResult = this.mockMvc
				.perform(put(URL + JOB_TITLE).content(objectMapper.writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();
		assertEquals("{\"status\":404,\"error\":\"Not Found\"," +
						"\"message\":\"No open dates with date 2022-03-28 was found.\"}",
				mvcResult.getResponse().getContentAsString());
	}

	@Test
	void doubleBookingVacationAsUserShouldNotBeAllowed() throws Exception  {
		ReservedDateDTO dto = new ReservedDateDTO(MID_SUMMER, USER_ID);

		LOGGER.info(LOG_DATE + MID_SUMMER);
		when(service.requestReservationUsingJobTitle(dto, JOB_TITLE))
				.thenThrow(new DoubleBookedException("A single user can only book a date once"));

		this.mockMvc.perform(MockMvcRequestBuilders.put(URL + JOB_TITLE)
						.content(objectMapper.writeValueAsString(dto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()))
				.andReturn();
	}

	@Test
	void userShouldBeAbleToResetFutureVacationDatesChoices() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete(URL + USER_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
	}
	
	/**
	 * Endpoint to retrieve List with TableScheduleDTO to be inserted in DB with vacant holiday dates.
	 * @author Nadia Hamid
	 */
	@Test
	void addScheduleTest() throws Exception {
		final int multiple = 1;
		String expectedJsonSchedule = objectMapper.writeValueAsString(
				new TableScheduleDTO(MID_SUMMER, MID_SUMMER.plusDays(2), multiple)
		);
		String actualJsonResponse = this.mockMvc
				.perform(MockMvcRequestBuilders.post(URL + JOB_TITLE)
				.content(expectedJsonSchedule)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals(expectedJsonSchedule, actualJsonResponse);
	}

	@Test
	void userShouldSeeAnnualVacationBookingData() throws Exception {
		final int pastBooked = 1, futureBooked = 1;
		final Set<OpenDateDTO> futureBookable = new HashSet<>();
		futureBookable.add(new OpenDateDTO(MID_SUMMER.plusDays(1)));
		futureBookable.add(new OpenDateDTO(MID_SUMMER.plusDays(2)));
		var userAnnualData = new UserAnnualDatesDTO(pastBooked, futureBooked, futureBookable);

		when(service.getMyAvailableDates(JOB_TITLE, USER_ID)).thenReturn(userAnnualData);

		MvcResult mvcResult = this.mockMvc.perform(get(URL + JOB_TITLE + "/" + USER_ID))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(userAnnualData);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void adminShouldSeeAnnualVacationBookingData() throws Exception {
		final long midSummerMultiple = 2L, single = 1L;
		final HashMap<LocalDate, Long> bookable = new HashMap<>();
		bookable.put(MID_SUMMER, midSummerMultiple);
		bookable.put(MID_SUMMER.plusDays(1), single);
		bookable.put(MID_SUMMER.plusDays(2), single);
		final var dto = new TableBookableDTO(bookable);

		when(service.getBookableByYearAndJobTitle(JOB_TITLE, String.valueOf(CURRENT))).thenReturn(dto);

		MvcResult mvcResult = this.mockMvc.perform(get(URL + JOB_TITLE  + "/" + "year" + "/" + CURRENT))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void startDateMustBeBeforeEndDate() throws Exception {
		String startDateAfterEndDate = "{\n" +
				"  \"startDate\": \"2022-06-02\",\n" +
				"  \"endDate\": \"2022-06-01\",\n" +
				"  \"multiple\": 1\n" +
				"}";
		doThrow(new TimeException("End time must be after start time!"))
				.when(service).addSchedule(any(TableScheduleDTO.class), any(String.class));
		String actualJsonResponse = this.mockMvc
				.perform(MockMvcRequestBuilders.post(URL + JOB_TITLE)
						.content(startDateAfterEndDate)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals("{\"status\":400,\"error\":\"Old vacation date\"," +
				"\"message\":\"End time must be after start time!\"}", actualJsonResponse);
	}

	@Test
	void getAllBookableDates() throws Exception {
		final long midSummerMultiple = 2L, single = 1L;
		final HashMap<LocalDate, Long> bookable = new HashMap<>();
		bookable.put(MID_SUMMER, midSummerMultiple);
		bookable.put(MID_SUMMER.plusDays(1), single);
		bookable.put(MID_SUMMER.plusDays(2), single);
		final var dto = new TableBookableDTO(bookable);

		when(service.getAllBookable()).thenReturn(dto);

		MvcResult mvcResult = this.mockMvc.perform(get(URL))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String actualResponseJson = mvcResult.getResponse().getContentAsString();
		String expectedResultJson = objectMapper.writeValueAsString(dto);
		assertEquals(expectedResultJson, actualResponseJson);
	}

	@Test
	void yearMustBeInteger() throws Exception {
		when(service.getBookableByYearAndJobTitle(JOB_TITLE, "two%20thousand"))
				.thenThrow(new NumberFormatException("Year must be integer!"));
		String actualJsonResponse = this.mockMvc.perform(get(URL + JOB_TITLE  + "/" + "year" + "/" + "two%20thousand"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertEquals("{\"status\":400,\"error\":\"Year must be an integer\"," +
				"\"message\":\"Year must be integer!\"}", actualJsonResponse);
	}
}
