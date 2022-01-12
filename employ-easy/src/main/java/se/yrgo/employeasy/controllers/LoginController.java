package se.yrgo.employeasy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@RestController("loginController")
public class LoginController {

    @RequestMapping(value = "/v1/greeting", method = RequestMethod.GET)
    public String greeting(@AuthenticationPrincipal(expression = "username") String username) {
        return "Hello, " + username + "!";
    }

    @RequestMapping(value = "/v1/token", method = RequestMethod.GET)
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping(value = "/v1/auth", method = RequestMethod.GET)
    public ResponseEntity<UserDetails> authUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(userDetails);
    }
}
