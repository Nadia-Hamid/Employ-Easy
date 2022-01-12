package se.yrgo.employeasy.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.yrgo.employeasy.dto.UserDTO;
import se.yrgo.employeasy.services.LoginService;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController("loginController")
@RequestMapping(value = "/v1/")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * @return Get user from its user details.
     */
    @Operation(summary = "Get user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authorization required to fetch the user",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content) })
    @RequestMapping(value = "greeting", method = RequestMethod.GET)
    public String welcome(@AuthenticationPrincipal(expression = "username") String username) {
        return loginService.greet(username);
    }

    /**
     * @return Get user from its user details.
     */
    @Operation(summary = "Get user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the token",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authorization required to fetch the token",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content) })
    @RequestMapping(value = "token", method = RequestMethod.GET)
    public Map<String, String> getCurrentToken(HttpSession session) {
        return loginService.getToken(session);
    }

    /**
     * @return Get user from its user details.
     */
    @Operation(summary = "Get user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Authorization required to fetch the user",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Accessing the resource you were trying to reach is forbidden", content = @Content) })
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public UserDTO getCurrentLogin() {
        return loginService.getUser();
    }
}
