package il.ac.afeka.fdp.course.layout;

import il.ac.afeka.fdp.course.data.boundary.CourseBoundary;
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

@RestController
@RequestMapping("/course/lecturer")
public class LecturerCourseController {
    @Autowired
    private CourseService courseService;


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

}
