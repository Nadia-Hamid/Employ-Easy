package se.yrgo.employeasy.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "se.yrgo.employeasy.login")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LoginSecurityTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getAllEmployeesWithoutAccess() throws Exception {
        this.mockMvc.perform(get("/v1/employees"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllEmployeesAsAdmin() throws Exception {
        this.mockMvc.perform(get("/v1/employees")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().encoding(StandardCharsets.ISO_8859_1));
    }

    @Test
    void greetingReturnsHelloAndUsername() throws Exception {
        this.mockMvc.perform(get("/v1/greeting").with(user("marten").roles("EMPLOYEE")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, marten!"));
    }

    @Test
    void getLocalSessionToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/token")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();
        final String json = mvcResult.getResponse().getContentAsString();
        assertEquals("{\"token\":", json.substring(0,9));
    }

    @Test
    void getCurrentlyLoggedInUserDetails() throws Exception {
        this.mockMvc.perform(get("/v1/auth").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"username\":\"admin\",\"authorities\":[{\"authority\":\"ROLE_ADMIN\"}]}"
                ));
    }

}
