package il.ac.afeka.fdp.course.layout;

import il.ac.afeka.fdp.course.exceptions.BadReqException;
import il.ac.afeka.fdp.course.data.Course;
import il.ac.afeka.fdp.course.data.CourseBoundary;
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

@CrossOrigin(maxAge = 3600)
@RestController
public class CourseController {
    private CourseService course;

    @Autowired
    public CourseController(CourseService course) {
        super();
        this.course = course;
    }

    /**
     * Create a new course -- POST
     */
    @ApiOperation(
            value = "Create a new course",
            notes = "Adds a course to the system\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = FinalStrings.COURSE_CREATED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.CREATE_BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = FinalStrings.COURSE_EXISTS),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR) })

    @RequestMapping(
            path = "/course",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CourseBoundary create(@RequestBody CourseBoundary newCourse) {
        return fromEntity(this.course.create(toEntity(newCourse)));
    }

    /**
     * Get All Courses -- GET
     */
    @ApiOperation(
            value = "Get all Courses",
            notes = "Get all courses from the database")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, response = CourseBoundary[].class, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/course",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CourseBoundary[] getAllCoursesByFilter(
            @RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
            @RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Course> rv = null;
        if (filterType.isEmpty()) {
            rv = this.course.getAllCourses(page, size, sort);
        } else {
            try {
                if (filterValue.isEmpty())
                    throw new RuntimeException("Value to search is empty");
                switch (filterType) {
                    case "courseName":
                        rv = this.course.getCourseByCourseName(filterValue, page, size, sort);
                        break;

                    case "department":
                        rv = this.course.getCourseByDepartment(filterValue, page, size, sort);
                        break;

                    case "software":
                        rv = this.course.getCourseBySoftwareList(filterValue, page, size, sort);
                        break;

                    default:
                        throw new RuntimeException("can't search by this type " + filterType);
                }

            } catch (Exception e) {
                throw new BadReqException(e.getMessage());
            }
        }
        return rv
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList())
                .toArray(new CourseBoundary[0]);
    }

    /**
     * Get Course by course code -- GET
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

    @RequestMapping(
            path = "/course/{courseCode}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CourseBoundary getCourseByCode(
            @PathVariable("courseCode") String courseCode) {
        return fromEntity(this.course.getCourseByCode(courseCode));
    }

    /**
     * Edit specific course -- PUT
     */
    @ApiOperation(
            value = "Edit course details by course code",
            notes = "Edit this course in database.\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.SPECIFIC_COURSE_EDITED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NO_COURSE_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/course/{courseCode}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editCourse(
            @PathVariable String courseCode,
            @RequestBody CourseBoundary courseEdit) {
        this.course.editCourse(courseCode,toEntity(courseEdit));
    }

    /**
     * Delete specific course -- DELETE
     */
    @ApiOperation(
            value = "Delete specific course by course code",
            notes = "Remove this course from the database.\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.SPECIFIC_COURSE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NO_COURSE_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/course/{courseCode}",
            method = RequestMethod.DELETE)
    public void deleteCourseByCode(
            @PathVariable("courseCode") String courseCode) {
        this.course.deleteCourseByCode(courseCode);
    }

    /**
     * Delete all courses -- DELETE
     */
    @ApiOperation(
            value = "Delete all courses",
            notes = "Delete courses from the database.\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.COURSES_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/course",
            method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        this.course.deleteAll();
    }

    /**
     * Transform from database object entity to user object boundary
     * @param course entity object
     * @return CourseBoundary object
     */
    private CourseBoundary fromEntity(Course course) {
        CourseBoundary rv = new CourseBoundary();
        rv.setCourseName(course.getCourseName());
        rv.setCourseCode(course.getCourseCode());
        rv.setDepartment(course.getDepartment());
        rv.setSoftwareList(course.getSoftwareList());
        rv.setId(course.getId());
        return rv;
    }

    /**
     * Transform from user object boundary to database object entity
     * @param course boundary object
     * @return course entity object
     */
    private Course toEntity(CourseBoundary course) {
        try {
            Course rv = new Course();
            rv.setCourseName(course.getCourseName());
            rv.setCourseCode(course.getCourseCode());
            rv.setDepartment(course.getDepartment());
            rv.setSoftwareList(course.getSoftwareList());
            return rv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
