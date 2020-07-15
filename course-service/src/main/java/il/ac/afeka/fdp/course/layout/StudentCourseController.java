package il.ac.afeka.fdp.course.layout;

import il.ac.afeka.fdp.course.data.boundary.CourseBoundary;
import il.ac.afeka.fdp.course.data.boundary.UserRole;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.exceptions.root.BadReqException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class StudentCourseController {

    @Autowired
    private CourseService courseService;

//    /**
//     * Get Course by course code -- GET
//     *
//     * @param code course's code to select
//     * @return course
//     */
//    @ApiOperation(
//            value = "Get course by its courseCode",
//            notes = "Get this course from the database")
//
//    @ApiResponses(value = {
//            @ApiResponse(code = HttpURLConnection.HTTP_OK, response = CourseBoundary.class, message = FinalStrings.OK),
//            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
//            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
//            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
//            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
//            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})
//
//    @GetMapping(
//            path = "/{code}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public CourseBoundary getCourseByCode(@PathVariable("code") long code) {
//        return new CourseBoundary(this.courseService.getCourseByCode(code));
//    }

    /**
     *
     * @param code course code
     * @param id lecturer id
     */
    @ApiOperation(
            value = "Assign STUDENT to course")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @PatchMapping(path = "/{code}")
    public void addStudentToCourse(@PathVariable(name = "code") long code,
                                    @RequestParam(name = "id") String id) {
        this.courseService.assign(code,id, UserRole.STUDENT);
    }

    /**
     *
     * @param code course code
     * @param id lecturer id
     */
    @ApiOperation(
            value = "Remove STUDENT from course")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.RESOURCE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @DeleteMapping(path = "/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeStudentFromCourse(@PathVariable(name = "code") long code,
                                    @RequestParam(name = "id") String id) {
        this.courseService.remove(code,id, UserRole.STUDENT);
    }

    @ApiOperation(
            value = "Get student courses")
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
        return this.courseService.findMyCourses(id, UserRole.STUDENT)
                .stream()
                .map(CourseBoundary::new)
                .collect(Collectors.toList());
    }

    @ApiOperation(
            value = "Get all courses")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CourseBoundary[] getAllCoursesByFilter(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1000") int size,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(name = "sort", required = false, defaultValue = "code") String sort,
            @RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
            @RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue
    ) {
        List<CourseEntity> rv;
        if (filterType.isEmpty()) {
            rv = this.courseService.getAllCourses(page, size, direction, sort);
        } else {
            try {
                switch (filterType) {
                    case "name":
                        rv = this.courseService.getCoursesByName(filterValue, page, size, direction, sort);
                        break;

                    case "code":
                        try {
                            rv = new ArrayList<>() {{
                                add(courseService.getCourseByCode(Integer.parseInt(filterValue)));
                            }};
                        } catch (NumberFormatException e) {
                            throw new BadReqException("Can't convert { " + filterValue + " } to int");
                        }
                        break;
                    default:
                        throw new BadReqException("can't search by this type " + filterType);
                }

            } catch (Exception e) {
                throw new BadReqException(e.getMessage());
            }
        }
        return rv
                .stream()
                .map(CourseBoundary::new).toArray(CourseBoundary[]::new);
    }
}