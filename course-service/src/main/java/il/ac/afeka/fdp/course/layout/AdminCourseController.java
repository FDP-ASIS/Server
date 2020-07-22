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

import javax.websocket.server.PathParam;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course/admin")
public class AdminCourseController {
    @Autowired
    private CourseService courseService;

    /**
     * Create a new courses -- POST
     *
     * @param courses to create
     * @return created courses
     */
    @ApiOperation(
            value = "Create new courses",
            notes = "Add courses to the system",
            nickname = "createCourse")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = FinalStrings.RESOURCE_CREATED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = FinalStrings.RESOURCE_EXISTS),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<CourseBoundary> createCourse(@RequestBody List<CourseBoundary> courses) {
        return this.courseService.create(courses.stream()
                .map(CourseBoundary::convertToEntity)
                .collect(Collectors.toList()))
                .stream()
                .map(CourseBoundary::new)
                .collect(Collectors.toList());
    }

    /**
     * @param page        to start
     * @param size        of the page
     * @param direction   to sort
     * @param sort        properties to sort
     * @param filterType  type
     * @param filterValue value
     * @return list of courses
     */
    @ApiOperation(
            value = "Get all Courses",
            notes = "Get all courses from the database")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, response = CourseBoundary[].class, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CourseBoundary[] getAllCoursesByFilter(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
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
//                    case "department":
//                        try {
//                            rv = this.courseService.getCoursesByDepartmentCode(Integer.parseInt(filterValue), page, size, direction, sort);
//                        } catch (NumberFormatException e) {
//                            throw new BadReqException("Can't convert { " + filterValue + " } to int");
//                        }
//                        break;
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

    /**
     * Edit specific course -- PUT
     *
     * @param code       course's code to change
     * @param courseEdit new course
     */
    @ApiOperation(
            value = "Edit course details by course code",
            notes = "Edit this course in database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.RESOURCE_EDITED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @PutMapping(
            path = "/{code}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editCourse(
            @PathVariable long code,
            @RequestBody CourseBoundary courseEdit) {
        this.courseService.editCourse(code, courseEdit.getCode() != null ? courseEdit.convertToEntity() : courseEdit.convertToEntity(code));
    }

    /**
     * Delete specific course -- DELETE
     *
     * @param code course's code to delete
     */
    @ApiOperation(
            value = "Delete specific course by course code",
            notes = "Remove this course from the database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.RESOURCE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})
    @DeleteMapping(path = "/{code}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCourseByCode(@PathVariable("code") long code) {
        this.courseService.deleteCourseByCode(code);
    }

    /**
     * Delete all courses -- DELETE
     */
    @ApiOperation(
            value = "Delete all courses",
            notes = "Delete courses from the database")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.RESOURCE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAllCourses() {
        this.courseService.deleteAll();
    }

    /**
     *
     * @param code course code
     * @param id lecturer id
     */
    @ApiOperation(
            value = "Assign lecturer to course")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @PatchMapping(path = "/{code}/{id}")
    public CourseBoundary addLecturerToCourse(@PathVariable(name = "code") long code,
                                              @PathVariable(name = "id") String id) {
        return new CourseBoundary(this.courseService.assign(code,id, UserRole.LECTURER));
    }

    /**
     *
     * @param code course code
     * @param id lecturer id
     */
    @ApiOperation(
            value = "Remove lecturer from course")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.RESOURCE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @DeleteMapping(path = "/{code}/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeLecturerFromCourse(@PathVariable(name = "code") long code,
                                         @PathVariable(name = "id") String id) {
        this.courseService.remove(code,id, UserRole.LECTURER);
    }
}