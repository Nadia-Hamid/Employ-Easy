package se.yrgo.employeasy.vacation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.dto.ReservedDateDTO;
import se.yrgo.employeasy.vacation.dto.TableScheduleDTO;
import se.yrgo.employeasy.vacation.dto.UserAnnualDatesDTO;
import se.yrgo.employeasy.vacation.entities.VacationDate;
import se.yrgo.employeasy.vacation.services.VacationService;

import java.util.List;
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
			@ApiResponse(responseCode = "200", description = "Successfully retrieved available vacations", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenDateDTO.class)))),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to find was not found", content = @Content) })
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
			@ApiResponse(responseCode = "200", description = "Successfully reserved the open date",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ReservedDateDTO.class))),
			@ApiResponse(responseCode = "400", description = "The reservation request was not in the future",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to update was not found",
					content = @Content) })
	@RequestMapping(value = "{jobTitle}", method = RequestMethod.PUT)
	public ReservedDateDTO reserveVacation(@RequestBody ReservedDateDTO reservationRequest, @PathVariable String jobTitle) {
		return vacationService.requestReservationUsingJobTitle(
				reservationRequest.getDate(),
				reservationRequest.getUserId(),
				jobTitle
		);
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
			@ApiResponse(responseCode = "200", description = "Successfully deleted future vacation choices", content = @Content) })
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
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
			@ApiResponse(responseCode = "400", description = "Illegal or corrupted data for request"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to update was not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error or unavailability.") })
	@RequestMapping(value = "{jobTitle}", method = RequestMethod.POST)
	public List<VacationDate> vacationSchedule(@PathVariable String jobTitle, @RequestBody TableScheduleDTO schedule) {
		return vacationService.addSchedule(jobTitle, schedule.getStartDate(), schedule.getEndDate(), schedule.getMultiple());
	}
}
