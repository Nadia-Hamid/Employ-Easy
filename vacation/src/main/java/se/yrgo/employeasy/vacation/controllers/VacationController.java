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
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OpenDateDTO.class)))),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to find was not found",
                    content = @Content)})
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

    @Operation(summary = "Reset all future vacations choices for user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted future vacation choices"
                    , content = @Content)})
    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
    public void resetFutureVacationChoices(@PathVariable String userId) {
        vacationService.resetFutureVacationChoices(userId);
    }

    //GET {jobTitle}/{userId} -> numberOfVacationChoicesThisYear, futureChoices, {future 1, future 2}

}
