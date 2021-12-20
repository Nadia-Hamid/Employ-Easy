package se.yrgo.employee.controllers;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/v1/auth", method = RequestMethod.GET)
    public ResponseEntity<UserDetails> authUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(userDetails);
    }
}
