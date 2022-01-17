package se.yrgo.employeasy.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employeasy.dto.UserDTO;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@Service("loginService")
@Transactional
public class LoginService {

    public UserDTO getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDTO(user.getUsername(), user.getAuthorities());
    }

    public String greet(String username) {
        return "Hello, " + username + "!";
    }

    public Map<String, String> getToken(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }
}
