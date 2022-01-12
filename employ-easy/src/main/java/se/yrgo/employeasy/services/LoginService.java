package se.yrgo.employeasy.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.employeasy.dto.UserDTO;
import se.yrgo.employeasy.repositories.UserRepository;

@Service("loginService")
@Transactional
public class LoginService {

    final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUser() {
        return new UserDTO(userRepository.getUsername(), userRepository.getAuthorities());
    }

}
