package se.yrgo.employeasy.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import se.yrgo.employeasy.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    @Autowired
    private LoginService loginServiceTest;

    /*
    public UserDTO getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDTO(userDetails);
    }
     */
    @Test
    void getUser() {
        UserDTO dto = loginServiceTest.getUser();
    }

}