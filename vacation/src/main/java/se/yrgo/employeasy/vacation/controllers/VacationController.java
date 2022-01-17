package se.yrgo.employeasy.vacation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.services.VacationService;

import java.time.LocalDate;
import java.util.List;

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
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available vacations",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OpenDateDTO.class)))),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to find was not found",
                    content = @Content)})
    @RequestMapping(value = "/{jobTitle}", method = RequestMethod.GET)
    public List<OpenDateDTO> getOpenVacations(@PathVariable String jobTitle) {
        return vacationService.getAllFromJobTitle(jobTitle);
    }

    @Operation(summary = "Delete an open date for developers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the open date", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to delete is not found",
                    content = @Content) })
    @RequestMapping(value = "/marmar1234/2022-06-20", method = RequestMethod.DELETE)
    public void deleteDate() {
        vacationService.deleteDateFromUser("marmar1234", LocalDate.of(2022, 6, 20));
    }

}
