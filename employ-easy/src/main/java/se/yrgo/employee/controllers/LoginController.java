package se.yrgo.employee.controllers;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@GetMapping("/v1/greeting")
	public String greeting(@AuthenticationPrincipal(expression = "username") String username) {
		return "Hello, " + username + "!";
	}
	
	@RequestMapping("/v1/token")
	public Map<String, String> token(HttpSession session) {
		return Collections.singletonMap("token", session.getId());
	}

}
