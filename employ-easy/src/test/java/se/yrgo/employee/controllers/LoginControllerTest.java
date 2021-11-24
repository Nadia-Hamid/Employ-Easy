package se.yrgo.employee.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LoginControllerTest {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
//
//    @Test
//    public void greetingReturnsHelloAndUsername() throws Exception {
//        this.mockMvc.perform(get("/v1/greeting").with(user("admin")))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Hello, admin!"));
//    }
}