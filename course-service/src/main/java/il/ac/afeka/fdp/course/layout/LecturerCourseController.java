package il.ac.afeka.fdp.course.layout;

import il.ac.afeka.fdp.course.data.boundary.CourseBoundary;
import il.ac.afeka.fdp.course.data.boundary.IdBoundary;
import il.ac.afeka.fdp.course.data.boundary.UserRole;
import il.ac.afeka.fdp.course.infra.CourseService;
import il.ac.afeka.fdp.course.utils.FinalStrings;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lecturer Course Controller methods
 */
@RestController
@RequestMapping("/course/lecturer")
public class LecturerCourseController {
    @Autowired
    private CourseService courseService;

    /**
     * Find lecturer courses -- GET
     * @param id id of the lecturer
     * @return List of lecturer courses
     */
    @ApiOperation(
            value = "Find lecturer courses")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CourseBoundary> getCourses(@PathVariable("id") String id) {
        return this.courseService.findMyCourses(id, UserRole.LECTURER)
                .stream()
                .map(CourseBoundary::new)
                .collect(Collectors.toList());
    }

    /**
     * Add software to course -- PATCH
     * @param code course code
     * @param idBoundary id of software
     * @return details of the specific course
     */
    @ApiOperation(
            value = "Add software to course")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @PatchMapping(value = "/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CourseBoundary addSoftware(
            @PathVariable("code") Long code,
            @RequestBody IdBoundary idBoundary
    ) {
        return new CourseBoundary(this.courseService.addSoftware(code, idBoundary.getId()));
    }

    /**
     * Remove software to course -- DELETE
     * @param code course code
     * @param id id of the software
     */
    @ApiOperation(
            value = "Remove software to course")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.RESOURCE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @DeleteMapping(value = "/{code}/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeSoftware(@PathVariable("code") Long code,
                               @PathVariable("id") String id) {
        this.courseService.removeSoftware(code, id);
    }
}