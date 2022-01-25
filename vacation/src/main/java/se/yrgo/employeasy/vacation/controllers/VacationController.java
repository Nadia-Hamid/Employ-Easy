package se.yrgo.employeasy.vacation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.employeasy.vacation.dto.*;
import se.yrgo.employeasy.vacation.services.VacationService;

import java.util.Set;

@RestController
@RequestMapping(value = "/v1/vacations/")
public class VacationController {

	private final VacationService vacationService;

	@Autowired
	public VacationController(VacationService vacationService) {
		this.vacationService = vacationService;
	}

	/**
	 * @return List of all available vacations for the job title.
	 */
	@Operation(summary = "Get all available vacations for position.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved available vacations",
					content = @Content(mediaType = "application/json"
							, array = @ArraySchema(schema = @Schema(implementation = OpenDateDTO.class)))),
			@ApiResponse(responseCode = "404", description = "No open dates was found", content = @Content) })
	@RequestMapping(value = "{jobTitle}", method = RequestMethod.GET)
	public Set<OpenDateDTO> getOpenVacations(@PathVariable String jobTitle) {
		return vacationService.getAllFromJobTitle(jobTitle);
	}

	/**
	 * @return ReservedDateDTO with reservation data.
	 * @param reservationRequest The reservation request.
	 */
	@Operation(summary = "Reserve an open date with unique userId using specified job title.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully reserved the open date.",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReservedDateDTO.class))),
			@ApiResponse(responseCode = "400", description = "The reservation request was not in the future.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Request another date preferable, otherwise email admin.",
					content = @Content),
			@ApiResponse(responseCode = "409", description = "The date has already been booked. Please choose another one.",
					content = @Content)})
	@RequestMapping(value = "{jobTitle}", method = RequestMethod.PUT)
	public ReservedDateDTO reserveVacation(@RequestBody ReservedDateDTO reservationRequest, @PathVariable String jobTitle) {
		return vacationService.requestReservationUsingJobTitle(reservationRequest, jobTitle);
	}

    /**
     * Get user's yearly selected and selectable dates.
     * @return User's current annual data
     */
    @Operation(summary = "Get user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the data",
                    content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = UserAnnualDatesDTO.class)))})
            @RequestMapping(value = "{jobTitle}/{userId}", method = RequestMethod.GET)
    public UserAnnualDatesDTO getOpenVacations(@PathVariable String jobTitle, @PathVariable String userId) {
        return vacationService.getMyAvailableDates(jobTitle, userId);
    }

	@Operation(summary = "Reset all future vacations choices for user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted future vacation choices",
					content = @Content) })
	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	public void resetFutureVacationChoices(@PathVariable String userId) {
		vacationService.resetFutureVacationChoices(userId);
	}

	/**
	 * @return TableScheduleDTO confirmation with date/job title data.
	 */
	@Operation(summary = "Post new dates into DB.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Insertions successfully completed.", 
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = TableScheduleDTO.class))),
			@ApiResponse(responseCode = "400", description = "The end time must be after start time.",
					content = @Content)})
			@RequestMapping(value = "{jobTitle}", method = RequestMethod.POST)
	public TableScheduleDTO vacationSchedule(@PathVariable String jobTitle, @RequestBody TableScheduleDTO schedule) {
		vacationService.addSchedule(schedule, jobTitle);
		return schedule;
	}

	/**
	 * Get yearly maximum simultaneous booked workers per day.
	 * @return Map with date and number
	 */
	@Operation(summary = "Get yearly data by job title.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the data",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = TableBookableDTO.class))),
			@ApiResponse(responseCode = "400", description = "The year must be an integer.",
					content = @Content),})
	@RequestMapping(value = "{jobTitle}/year/{year}", method = RequestMethod.GET)
	public TableBookableDTO getBookableDates(@PathVariable String jobTitle, @PathVariable String year) {
		return vacationService.getBookableByYearAndJobTitle(jobTitle, year);
	}

	/**
	 * Get this year's maximum simultaneous booked workers per day.
	 * @return Map with date and number
	 */
	@Operation(summary = "Get current year data.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the data",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = TableBookableDTO.class)))})
	@RequestMapping(value = "", method = RequestMethod.GET)
	public TableBookableDTO getAllBookableDates() {
		return vacationService.getAllBookable();
	}
}
