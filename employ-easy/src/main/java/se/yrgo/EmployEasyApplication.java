package se.yrgo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@OpenAPIDefinition(
    info = @Info(title = "Employ Easy API", version = "1.2", description = "Employee Management Application")
)
public class EmployEasyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployEasyApplication.class, args);
    }
}
