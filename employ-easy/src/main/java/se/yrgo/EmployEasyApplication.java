package se.yrgo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * class EmployeEasyApplication
 * abstract Application class running a spring boot application.
 * updated 2022-01-20
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@OpenAPIDefinition(
    info = @Info(title = "Employ Easy API", version = "1.3", description = "Employee Management Application")
)
public class EmployEasyApplication {

    /**
     * Simple main method initializing spring boot app
     * @param args Arguments provided for running the app.
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployEasyApplication.class, args);
    }
}
