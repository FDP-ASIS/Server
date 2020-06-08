package il.ac.afeka.fdp.course.layout;

import il.ac.afeka.fdp.course.exceptions.BadReqException;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.data.boundary.CourseBoundary;
import il.ac.afeka.fdp.course.infra.CourseService;
import il.ac.afeka.fdp.course.utils.FinalStrings;
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
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService course;

    /**
     * Get Course by course code -- GET
     *
     * @param courseCode course's code to select
     * @return course
     */
    @ApiOperation(
            value = "Get course by its courseCode",
            notes = "Get this course from the database")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, response = CourseBoundary.class, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NO_COURSE_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @GetMapping(
            path = "/course/{courseCode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CourseBoundary getCourseByCode(@PathVariable("courseCode") long courseCode) {
        return new CourseBoundary(this.course.getCourseByCode(courseCode));
    }
}
