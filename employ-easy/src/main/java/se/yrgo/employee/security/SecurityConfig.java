package se.yrgo.employee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())
                .authorizeRequests(authz -> authz
                        .antMatchers(HttpMethod.GET, "/v1/employees").permitAll()
                        .antMatchers(HttpMethod.POST, "/v1/employees").hasRole("ADMIN")
                        .antMatchers("/v1/greeting").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

}
