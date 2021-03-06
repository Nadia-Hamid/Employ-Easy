package se.yrgo.employeasy.security;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * class PasswordConfig
 * abstract Security Configuration and CorsFilter for Login.
 * updated 2022-01-19
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * User name and role configuration.
     * @param auth Spring security manager builder for Authentication
     * @throws Exception Not in use. See https://github.com/spring-projects/spring-security/pull/7580
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("admin")
            .password(passwordEncoder.encode("password123"))
            .roles("ADMIN")
            .and()
            .withUser("marher1235")
            .password(passwordEncoder.encode("password"))
            .roles("EMPLOYEE")
                .and()
                .withUser("marmar1234")
                .password(passwordEncoder.encode("marmar1234"))
                .roles("EMPLOYEE")
                .and()
                .withUser("nadham4321")
                .password(passwordEncoder.encode("nadham4321"))
                .roles("ADMIN");

    }

    /**
     * Cors and csrf disabler for admin role
     * @param http Spring security https
     * @throws Exception Not in use. See https://github.com/spring-projects/spring-security/pull/7580
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/", "index", "/css/*", "/js/*")
            .permitAll()
            .antMatchers(HttpMethod.POST, "/v1/employees")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/v1/employees")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/v1/auth")
            .hasAnyRole("ADMIN", "EMPLOYEE")
            .antMatchers(HttpMethod.GET, "/v1/greeting")
            .hasAnyRole("ADMIN", "EMPLOYEE")
            .antMatchers(HttpMethod.GET, "/v1/employees")
            .hasAnyRole("ADMIN")
            .antMatchers("/login*")
            .permitAll()
            .anyRequest()
            .fullyAuthenticated()
            .and()
            .httpBasic();
    }

    /**
     * Cors filter for ports. Only Angular frontend should have access (4200)
     * @return Current cors filter.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(
            Arrays.asList(
                "Origin",
                "Access-Control-Allow-Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "Origin, Accept",
                "X-Requested-With",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
            )
        );
        corsConfiguration.setExposedHeaders(
            Arrays.asList(
                "Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"
            )
        );
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
