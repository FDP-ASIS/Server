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

/**
 * Auth Controller methods
 */
@RestController
@RequestMapping("/auth")
@Api(value = "Auth controller")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Log the user in with username and password -- POST
     * @param UsernamePasswordBoundary credentials
     * @return UserWithToken
     */
    @ApiOperation(value = "Log the user in with username and password", nickname = "login")
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
     * Request the token to check (Authenticate user with token) -- POST
     * @param token string
     * @return userBoundary
     */
    @ApiOperation(value = "Authenticate user with token",
                  nickname = "tokenAuth")
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
     * Logout the user from the system and delete his token -- DELETE
     */
    @ApiOperation(value = "Logout the user from the system and delete his token",
                  nickname = "logout")
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