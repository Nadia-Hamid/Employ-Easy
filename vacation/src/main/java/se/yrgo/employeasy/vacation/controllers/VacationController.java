package se.yrgo.employeasy.vacation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.services.VacationService;

@RestController
@RequestMapping(value = "/v1/vacations")
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
	@RequestMapping(value = "/{jobTitle}", method = RequestMethod.GET)
	public List<OpenDateDTO> getOpenVacations(@PathVariable String jobTitle) {
		return vacationService.getAllFromJobTitle(jobTitle);
	}

    /**
	 * @return Object confirmation with date data.
	 */
	@Operation(summary = "Post a date into vacation schedule.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Booking successfully completed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OpenDateDTO.class))),
			@ApiResponse(responseCode = "400", description = "Illegal or corrupted data for request"),
			@ApiResponse(responseCode = "404", description = "The resource you were trying to update was not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error or unavailability.") })
	@RequestMapping(value = "", method = RequestMethod.POST)
	public OpenDateDTO vacationBooking(@RequestBody OpenDateDTO openDate) {
		return vacationService.addVacation(openDate);
	}
}
