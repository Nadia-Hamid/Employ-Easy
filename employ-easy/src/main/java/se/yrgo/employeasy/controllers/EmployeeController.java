package se.yrgo.employeasy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.yrgo.employeasy.dto.EmployeeDTO;
import se.yrgo.employeasy.services.EmployeeService;

import java.util.List;

@RestController("employeeController")
@RequestMapping(value = "/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @return List of all employees.
     */
    @Operation(summary = "Get all employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved employees", content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Authorization required to fetch employees", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
    })
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAll();
    }

    /**
     * @return Get employee from its user id.
     * @param userId The first three letters in first and last name with random four numbers.
     */
    @Operation(summary = "Get employee from userId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "401",
                    description = "Authorization required to fetch the employee", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The resource you were trying to reach is not found", content = @Content)})
    @GetMapping(value = "/{userId}")
    public EmployeeDTO getByUserId(@PathVariable String userId) {
        return employeeService.getByUserId(userId);
    }

    /**
     * @return Get all employees using the same job title.
     * @param jobTitle The job title searched for.
     */
    @Operation(summary = "Get employees from jobTitle.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved employees", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Authorization required to fetch employees", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The resource you were trying to find was not found", content = @Content)
    })
    @RequestMapping(value = "/jobtitle/{jobTitle}", method = RequestMethod.GET)
    public List<EmployeeDTO> findByJobTitle(@PathVariable String jobTitle) {
        return employeeService.findByJobTitle(jobTitle);
    }

    /**
     * @return Employee added in database with its user id.
     * @param registerDTO Input data without any userId.
     */
    @Operation(summary = "Register new employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully registered the employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Illegal employee/system status code or email was null", content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Authorization required to register the employee", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Resource with unique email has already been posted previously", content = @Content)})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public EmployeeDTO registerEmployee(@RequestBody EmployeeDTO registerDTO) {
        return employeeService.addEmployee(registerDTO);
    }

    /**
     * @param userId The userId of the employee to be deleted.
     */
    @Operation(summary = "Delete an employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully deleted the  employees", content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Authorization required to fetch employees", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The resource you were trying to delete is not found", content = @Content)
    })
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable String userId) {
        employeeService.deleteEmployee(userId);
    }

    /**
     * @return Employee with updated data.
     * @param employeeDTO The new data that should be updated.
     */
    @Operation(summary = "Update an employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully updated an employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Illegal employee/system status code or email was null", content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Authorization required to update the employee", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The resource you were trying to update was not found", content = @Content)})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(employeeDTO);
    }

    /**
     * @return Get the employee using provided email.
     * @param email The email searched for.
     */
    @Operation(summary = "Get employee from email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "401",
                    description = "Authorization required to fetch employee", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The resource you were trying to find was not found", content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Multiple instances of unique resource was found", content = @Content)
    })
    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public EmployeeDTO findUniqueEmail(@PathVariable String email) {
        return employeeService.findByEmail(email);
    }
}
