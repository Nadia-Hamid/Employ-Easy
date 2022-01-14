package se.yrgo.employeasy.vacation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.yrgo.employeasy.vacation.dto.OpenDateDTO;
import se.yrgo.employeasy.vacation.services.VacationService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VacationController.class)
public class VacationControllerTest {

    @MockBean
    private VacationService service;

    @Autowired
    private VacationController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDeveloperVacations() throws Exception {
        final List<OpenDateDTO> dtos = new ArrayList<>();
        dtos.add(new OpenDateDTO());
        when(service.getAllFromJobTitle("developer")).thenReturn(dtos);

        MvcResult mvcResult = mockMvc
                .perform(get("/v1/vacations/developers"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
    }
}
