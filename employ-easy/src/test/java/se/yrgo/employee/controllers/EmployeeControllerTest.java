package se.yrgo.employee.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.yrgo.employee.dto.EmployeeDTO;
import se.yrgo.employee.entities.Employee;
import se.yrgo.employee.entities.enums.EmployeeStatus;
import se.yrgo.employee.entities.enums.SystemStatus;
import se.yrgo.employee.security.PasswordConfig;
import se.yrgo.employee.security.SecurityConfig;
import se.yrgo.employee.services.EmployeeService;

@ContextConfiguration(classes = { SecurityConfig.class, PasswordConfig.class })
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    private EmployeeService mockedEmployeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL = "/v1/employees";

    @Test
    void getAllEmployees() throws Exception {
        when(mockedEmployeeService.findAll()).thenReturn(new ArrayList<>());
        MvcResult result = mockMvc
            .perform(get(URL).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound())
            .andReturn();
        String actualResponseJson = result.getResponse().getContentAsString();
        assertEquals("", actualResponseJson);
    }

    @Test
    void deleteEmployee() throws Exception {
        final String user = "marher1234";
        MvcResult result = mockMvc
            .perform(delete(URL + "/" + user).with(user("admin").roles("ADMIN")))
            .andExpect(status().isNotFound())
            .andReturn();
        String actualResponseJson = result.getResponse().getContentAsString();
        assertEquals("", actualResponseJson);
    }

    @Test
    void editEmployee() throws Exception {
        Employee savedEmployee = new Employee(
            "Nadia",
            "Hamid",
            "900519-XXXX",
            "Nadia@gmail.com",
            "87654321",
            "Norra Vägen",
            "44556",
            "Göteborg",
            "developer",
            "saab",
            LocalDate.of(2005, 1, 1),
            null,
            EmployeeStatus.VACATION,
            SystemStatus.SYSTEM_ADMIN
        );
        EmployeeDTO changeRequest = new EmployeeDTO(savedEmployee);
        final String email = "new@email.com";
        changeRequest.setEmail(email);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .put(URL)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(changeRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .with(user("admin").roles("ADMIN"));
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();

        String actualResponseJson = result.getResponse().getContentAsString();
        assertEquals("", actualResponseJson);
    }
}
