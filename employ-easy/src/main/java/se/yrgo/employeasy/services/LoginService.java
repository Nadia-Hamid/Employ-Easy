package se.yrgo.employeasy.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employeasy.dto.UserDTO;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

/**
 * class LoginService
 * abstract Service class with use cases not connected to any databases.
 * updated 2022-01-20
 */
@Service("loginService")
@Transactional
public class LoginService {

    /**
     * Provides login username and roles for frontend use.
     * @return User transfer object for exposing username and role to frontend.
     */
    public UserDTO getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDTO(user.getUsername(), user.getAuthorities());
    }

    /**
     * Simple greeting generator
     * @param username Login username
     * @return simple greeting
     */
    public String greet(String username) {
        return "Hello, " + username + "!";
    }

    /**
     * Http session token generator
     * @param session Current http session for the client
     * @return JSON where token value is http session id
     */
    public Map<String, String> getToken(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }
}
