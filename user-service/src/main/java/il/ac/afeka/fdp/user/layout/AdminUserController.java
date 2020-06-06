package il.ac.afeka.fdp.user.layout;

import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.boundary.UserBoundary;
import il.ac.afeka.fdp.user.infra.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/admin")
@Api(value = "Admin user controller")
public class AdminUserController {

    @Autowired
    private UserService userService;

    /**
     * @param boundaries users
     * @return saved users
     */
    @ApiOperation(value = "sign up users", notes = "Be careful with the query param", nickname = "signup")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created users"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    @PostMapping(value = {"/signup/{role}", "/signup"}, name = "sign up users by admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserBoundary> signUp(
            @PathVariable(value = "role", required = false) Optional<UserRoleEnum> role,
            @RequestBody List<UserBoundary> boundaries) {
        if (role.isEmpty())
            role = Optional.of(UserRoleEnum.STUDENT);
        return this.userService.signUp(boundaries.stream().map(UserBoundary::convertToEntity).collect(Collectors.toList()),role.get()).stream().map(UserBoundary::new).collect(Collectors.toList());
    }
}