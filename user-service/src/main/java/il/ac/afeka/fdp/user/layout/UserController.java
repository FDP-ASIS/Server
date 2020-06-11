package il.ac.afeka.fdp.user.layout;

import il.ac.afeka.fdp.user.data.boundary.PasswordBoundary;
import il.ac.afeka.fdp.user.data.boundary.UserBoundary;
import il.ac.afeka.fdp.user.infra.UserService;
import il.ac.afeka.fdp.user.utils.FinalStrings;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * @param id user's id need to change password
     * @param password need password to change
     * @return user
     */
    @ApiOperation(value = "Update user password by id", nickname = "updateUserPassword")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "You are not authorized to view the resource"),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "User not found"),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Other internal server errors")
    })
    @PatchMapping(value = "/{id}", name = "Update user password by id")
    UserBoundary updateUserPasswordById(
            @PathVariable(value = "id") String id,
            @RequestBody PasswordBoundary password) {
        return new UserBoundary(this.userService.updateUserPasswordById(id, password.getPassword()));
    }
}
