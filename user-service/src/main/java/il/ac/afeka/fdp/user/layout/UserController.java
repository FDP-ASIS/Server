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
     * @param passwordBoundary old password and new password
     * @return user
     */
    @ApiOperation(value = "Update user password by id", nickname = "updateUserPassword")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @PatchMapping(value = "/{id}", name = "Update user password by id")
    UserBoundary updateUserPasswordById(
            @PathVariable(value = "id") String id,
            @RequestBody PasswordBoundary passwordBoundary) {
        return new UserBoundary(this.userService.updateUserPasswordById(id, passwordBoundary.getOldPassword(), passwordBoundary.getNewPassword()));
    }
}
