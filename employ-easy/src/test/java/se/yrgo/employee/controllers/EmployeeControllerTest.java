package se.yrgo.employee.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.services.EmployeeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @MockBean
    private EmployeeService mockedEmployeeService;

    @Test
    void getAllEmployees() {
        /*public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		List<Employee> list = employeeService.findAll();
		List<EmployeeDTO> listDTO = list.stream()
				.map(EmployeeDTO::new)
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}*/
        List<EmployeeDTO> employeeList= new ArrayList<>();
        EmployeeDTO marius = new EmployeeDTO("marmar1234", "Marius", "Marthinussen", "890519-XXXX", "Marius@gmail.com", "12345678",
                "Södra Vägen", "44556", "Göteborg", "Developer", LocalDate.of(2000, 1, 1));
        marius.setParentCompany("Volvo");
        employeeList.add(marius);

        /*
        userId = object.getUserId();
		firstName = object.getfirstName();
		lastName = object.getLastName();
		personalNumber = object.getPersonalNumber();
		email = object.getEmail();
		phoneNumber = object.getPhoneNumber();
		street = object.getStreet();
		zip = object.getZip();
		city = object.getCity();
		jobTitle = object.getJobTitle();
		parentCompany = object.getParentCompany();
		startDate = object.getStartDate();
		endDate = object.getEndDate();
         */
/*
        when(mockedEmployeeService.getAllEmployees()).thenReturn(employeeList);
        String url="/v1/employees";
        MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualResponseJson= result.getResponse().getContentAsString();
        System.out.println( actualResponseJson);

        String expectedResultJson= objectMapper.writeValueAsString(listOfChallenge);
        assertEquals(expectedResultJson,actualResponseJson);*/
    }
}