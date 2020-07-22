package il.ac.afeka.fdp.auth.layout;

import il.ac.afeka.fdp.auth.data.UserWithToken;
import il.ac.afeka.fdp.auth.data.Token;
import il.ac.afeka.fdp.auth.data.boundary.UserBoundary;
import il.ac.afeka.fdp.auth.data.boundary.UsernamePasswordBoundary;
import il.ac.afeka.fdp.auth.infra.AuthService;
import il.ac.afeka.fdp.auth.utils.FinalStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth controller")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Login user to the system and create a new session token
     * @param UsernamePasswordBoundary credentials
     * @return UserWithToken
     */
    @ApiOperation(value = "Log the user into the system with username and password and create a new session token", nickname = "login")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.LOGIN),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.INVALID_CREDENTIALS),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR),
    })
    @PostMapping(
            value = "/login",
            name = "login user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserWithToken login(
            @RequestBody UsernamePasswordBoundary UsernamePasswordBoundary) {
        return this.authService.login(UsernamePasswordBoundary.getUsername(), UsernamePasswordBoundary.getPassword());
    }

    /**
     *
     * Authenticate user with token
     * @param token string
     * @return user
     */
    @ApiOperation(value = "Request only the token to check if it is correct and exists.\n" +
            "It is checking for access, and preventing outside hacking and unauthorized persons from using the system\n", nickname = "tokenAuth")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.LOGIN_JWT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.INVALID_CREDENTIALS),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR),
    })
    @PostMapping(
            name = "Authenticate user with token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserBoundary authWithToken(
            @RequestBody Token token) {
        return this.authService.auth(token);
    }

    /**
     * Logout user from the system and delete his token
     */
    @ApiOperation(value = "Logout the user from the system and delete the token", nickname = "logout")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_RESET, message = FinalStrings.RESET_CONTENT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR),
    })
    @DeleteMapping(value = "/logout", name = "logout user")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    void logout() {
        this.authService.logout();
    }
}