package il.ac.afeka.fdp.auth.layout;

import il.ac.afeka.fdp.auth.data.boundary.UserBoundary;
import il.ac.afeka.fdp.auth.data.boundary.UsernamePasswordBoundary;
import il.ac.afeka.fdp.auth.infra.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth controller")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * @param UsernamePasswordBoundary credentials
     * @return UserBoundary
     */
    @ApiOperation(value = "login user", nickname = "login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully login users"),
            @ApiResponse(code = 401, message = "Invalid credentials"),
    })
    @PostMapping(value = "/login", name = "login user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    UserBoundary login(
            @RequestBody UsernamePasswordBoundary UsernamePasswordBoundary) {
        return new UserBoundary(this.authService.login(UsernamePasswordBoundary.getUsername(), UsernamePasswordBoundary.getPassword()));
    }

    /**
     *
     */
    @ApiOperation(value = "logout user", nickname = "logout")
    @ApiResponses(value = {
            @ApiResponse(code = 205, message = "Successfully logout users"),
            @ApiResponse(code = 400, message = "Bad request"),
    })
    @DeleteMapping(value = "/logout", name = "logout user")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    void logout() {
        this.authService.logout();
    }
}
