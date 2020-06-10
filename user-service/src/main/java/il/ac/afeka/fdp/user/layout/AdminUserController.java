package il.ac.afeka.fdp.user.layout;

import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.boundary.UserBoundary;
import il.ac.afeka.fdp.user.infra.UserService;
import il.ac.afeka.fdp.user.utils.FinalStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.List;
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
    @ApiOperation(value = "sign up users", notes = "Be careful with the query param", nickname = "signUp")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = FinalStrings.RESOURCE_CREATED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = FinalStrings.RESOURCE_EXISTS),
    })
    @PostMapping(value = "/signup/{role}", name = "sign up users by admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    List<UserBoundary> signUp(
            @PathVariable(value = "role", required = false) UserRoleEnum role,
            @RequestBody List<UserBoundary> boundaries) {
        if (role == null)
            role = UserRoleEnum.STUDENT;
        return this.userService.signUp(boundaries.stream().map(UserBoundary::convertToEntity).collect(Collectors.toList()), role).stream().map(UserBoundary::new).collect(Collectors.toList());
    }

    /**
     * @param page      number of page
     * @param size      page size
     * @param direction direction
     * @param sort      properties
     * @return list of users
     */
    @ApiOperation(value = "Get all users", notes = "default sort is id", nickname = "getAll")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN)
    })
    @GetMapping(value = "/all", name = "Get all the users in the system using pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserBoundary> getAllUsers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort) {
        return this.userService.getAllUsers(page, size, direction, sort).stream().map(UserBoundary::new).collect(Collectors.toList());
    }

    /**
     * @param id user's id want to get
     * @return User
     */
    @ApiOperation(value = "Get user", nickname = "getUser")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
    })
    @GetMapping(value = "/{id}", name = "Get all the users in the system using pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    UserBoundary getAllUsers(
            @PathVariable(value = "id") String id) {
        return new UserBoundary(this.userService.getUserById(id));
    }

    /**
     * delete all the users in the system
     */
    @ApiOperation(value = "Delete all users", nickname = "deleteAllUsers")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.ALL_RESOURCES_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
    })
    @DeleteMapping(value = "/", name = "Delete all the users in the system")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void deleteAllUsers() {
        this.userService.deleteAllUsers();
    }

    /**
     * @param id user's id need to delete
     */
    @ApiOperation(value = "Delete a users", nickname = "deleteUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = FinalStrings.RESOURCE_DELETED),
            @ApiResponse(code = 401, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = 403, message = FinalStrings.FORBIDDEN),
    })
    @DeleteMapping(value = "/{id}", name = "Delete user by id")
    void deleteUserById(
            @PathVariable(value = "id") String id) {
        this.userService.deleteUserById(id);
    }

    /**
     * @param id           user's id need to update
     * @param userBoundary new user information that need to replace
     */
    @ApiOperation(value = "Update a user", nickname = "updateUser")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.RESOURCE_EDITED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
    })
    @PutMapping(value = "/{id}", name = "Update user by id")
    void updateUserById(
            @PathVariable(value = "id") String id,
            @RequestBody UserBoundary userBoundary) {
        this.userService.updateUserById(userBoundary.convertToEntity(id));
    }
}