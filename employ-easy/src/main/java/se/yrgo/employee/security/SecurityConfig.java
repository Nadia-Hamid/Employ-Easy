package se.yrgo.employee.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic(withDefaults())
				.authorizeRequests(authz -> authz.antMatchers(HttpMethod.GET, "/v1/employees")
						.permitAll()
						.antMatchers(HttpMethod.POST, "/v1/employees")
						.hasRole("ADMIN")
						.antMatchers("/v1/greeting")
						.permitAll()
						.anyRequest()
						.authenticated());
		return http.build();
	}

	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder.encode("password"))
				.roles("EMPLOYEE")
				.build();
		
		UserDetails admin = User.builder()
				.username("user2")
				.password(passwordEncoder.encode("password123"))
				.roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}
}
