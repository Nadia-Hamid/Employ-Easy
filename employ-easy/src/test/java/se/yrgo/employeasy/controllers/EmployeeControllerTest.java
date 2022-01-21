package se.yrgo.employeasy.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import se.yrgo.employeasy.dto.EmployeeDTO;
import se.yrgo.employeasy.entities.enums.EmployeeStatus;
import se.yrgo.employeasy.entities.enums.SystemStatus;
import se.yrgo.employeasy.security.PasswordConfig;
import se.yrgo.employeasy.security.SecurityConfig;
import se.yrgo.employeasy.services.EmployeeService;

@ComponentScan(basePackages = "se.yrgo.employeasy.controllers")
@ContextConfiguration(classes = { SecurityConfig.class, PasswordConfig.class })
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    private EmployeeService service;

    @Autowired
    private EmployeeController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL = "/v1/employees/";

    private EmployeeDTO nadia;

    @BeforeEach
    void setUp() {
        var mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        this.mockMvc =
            MockMvcBuilders
                .standaloneSetup(controller)
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .build();

        nadia =
            new EmployeeDTO(
                "Nadia",
                "Hamid",
                "900519-XXXX",
                "Nadia@gmail.com",
                "87654321",
                "Norra Vagen",
                "44556",
                "Goteborg",
                "developer",
                "saab",
                LocalDate.of(2005, 1, 1),
                null,
                EmployeeStatus.VACATION,
                SystemStatus.SYSTEM_ADMIN
            );
        nadia.setUserId("nadham4321");
    }

    @Test
    void getAllEmployeesTest() throws Exception {
        final List<EmployeeDTO> dtos = new ArrayList<>();
        dtos.add(nadia);
        EmployeeDTO marius = new EmployeeDTO(
            "Marius",
            "Marthinussen",
            "881005-XXXX",
            "marius@gmail.com",
            "90654321",
            "Sodra Vagen",
            "44556",
            "Goteborg",
            "developer",
            "saab",
            LocalDate.of(2005, 1, 1),
            null,
            EmployeeStatus.ACTIVE,
            SystemStatus.USER
        );
        marius.setUserId("marmar1234");
        dtos.add(marius);

        when(service.findAll()).thenReturn(dtos);
        MvcResult mvcResult = mockMvc
            .perform(get(URL).with(user("admin").roles("ADMIN")))
            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
            .andReturn();

        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        String expectedResultJson = objectMapper.writeValueAsString(dtos);
        assertEquals(expectedResultJson, actualResponseJson);
    }

    @Test
    void registerEmployeeTest() throws Exception {
        when(service.addEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(nadia);
        MvcResult mvcResult =
            this.mockMvc.perform(
                    MockMvcRequestBuilders
                        .post(URL)
                        .with(user("admin").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(nadia))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        String expectedResultJson = objectMapper.writeValueAsString(nadia);
        assertEquals(expectedResultJson, actualResponseJson);
    }

    @Test
    void deleteEmployeeTest() throws Exception {
        MvcResult mvcResult = mockMvc
            .perform(delete(URL + "/" + nadia.getUserId()).with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk())
            .andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void editEmployeeTest() throws Exception {
        final String email = "new@email.com";
        assertNotEquals(email, nadia.getEmail());

        nadia.setEmail(email);
        when(service.updateEmployee(Mockito.any(EmployeeDTO.class))).thenReturn(nadia);
        MvcResult mvcResult =
            this.mockMvc.perform(
                    MockMvcRequestBuilders
                        .put(URL)
                        .with(user("admin").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(nadia))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        String expectedResponseJson = objectMapper.writeValueAsString(nadia);
        assertEquals(expectedResponseJson, actualResponseJson);
    }

    @Test
    void findEqualEmailTest() throws Exception {
        when(service.findByEmail(Mockito.any(String.class))).thenReturn(nadia);
        MvcResult mvcResult =
            this.mockMvc.perform(
                    MockMvcRequestBuilders
                        .get(URL + "/email/nadia@gmail.com")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        String expectedResponseJson = objectMapper.writeValueAsString(nadia);
        assertEquals(expectedResponseJson, actualResponseJson);
    }

    @Test
    void findByJobTitleTest() throws Exception {
        final List<EmployeeDTO> dtos = new ArrayList<>();
        dtos.add(nadia);
        EmployeeDTO marius = new EmployeeDTO(
            "Marius",
            "Marthinussen",
            "881005-XXXX",
            "marius@gmail.com",
            "90654321",
            "Sodra Vagen",
            "44556",
            "Goteborg",
            "developer",
            "saab",
            LocalDate.of(2005, 1, 1),
            null,
            EmployeeStatus.ACTIVE,
            SystemStatus.USER
        );
        marius.setUserId("marmar1234");
        dtos.add(marius);

        when(service.findByJobTitle(Mockito.any(String.class))).thenReturn(dtos);
        MvcResult mvcResult =
            this.mockMvc.perform(
                    MockMvcRequestBuilders
                        .get(URL + "/jobtitle/developer")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        String expectedResultJson = objectMapper.writeValueAsString(dtos);
        assertEquals(expectedResultJson, actualResponseJson);
    }

    @Test
    void findByUserIdTest() throws Exception {
        when(service.getByUserId(Mockito.any(String.class))).thenReturn(nadia);
        MvcResult mvcResult =
            this.mockMvc.perform(
                    MockMvcRequestBuilders
                        .get(URL + "/" + nadia.getUserId())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn();

        String actualResponseJson = mvcResult.getResponse().getContentAsString();
        String expectedResultJson = objectMapper.writeValueAsString(nadia);
        assertEquals(expectedResultJson, actualResponseJson);
    }
}
