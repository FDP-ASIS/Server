package il.ac.afeka.fdp.course.layout;

import il.ac.afeka.fdp.course.data.boundary.DepartmentBoundary;
import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import il.ac.afeka.fdp.course.exceptions.root.BadReqException;
import il.ac.afeka.fdp.course.infra.DepartmentService;
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
@RequestMapping("/course/department/admin")
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Create a new departments -- POST
     *
     * @param departments to create
     * @return created courses
     */
    @ApiOperation(
            value = "Create new departments",
            notes = "Adds departments to the system",
            nickname = "createDepartments")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = FinalStrings.RESOURCE_CREATED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = FinalStrings.RESOURCE_EXISTS),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR),
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<DepartmentBoundary> create(@RequestBody List<DepartmentBoundary> departments) {
        return this.departmentService.create(departments.stream()
                .map(DepartmentBoundary::convertToEntity)
                .collect(Collectors.toList()))
                .stream()
                .map(DepartmentBoundary::new)
                .collect(Collectors.toList());
    }


    /**
     *
     * @param page      to start
     * @param size      of the page
     * @param direction to sort
     * @param sort      properties to sort
     * @param filterType type
     * @param filterValue filter
     * @return list of departments
     */
    @ApiOperation(
            value = "Get all Departments",
            notes = "Get all departments from the database")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, response = DepartmentBoundary[].class, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DepartmentBoundary[] getAllDepartmentsByFilter(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sort,
            @RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
            @RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue
    ) {
        List<DepartmentEntity> rv;
        if (filterType.isEmpty()) {
            rv =  this.departmentService.getAllDepartments(page, size, direction, sort);
        } else {
            try {
                if (filterValue.isEmpty())
                    throw new RuntimeException("Value to search is empty");
                switch (filterType) {
                    case "name":
                        rv = this.departmentService.getDepartmentsByName(filterValue, page, size, direction, sort);
                        break;
                    case "code":
                        try {
                            rv = this.departmentService.getDepartmentsByCode(Integer.parseInt(filterValue), page, size, direction, sort);
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
                .map(DepartmentBoundary::new)
                .toArray(DepartmentBoundary[]::new);
    }

    /**
     * Edit specific department -- PUT
     *
     * @param code               department's code to change
     * @param departmentBoundary new department
     */
    @ApiOperation(
            value = "Edit department details by department's code")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.RESOURCE_EDITED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @PutMapping(
            path = "/{code}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editDepartment(
            @PathVariable("code") int code,
            @RequestBody DepartmentBoundary departmentBoundary) {
        this.departmentService.editDepartment(code, departmentBoundary.getCode() != null ? departmentBoundary.convertToEntity() : departmentBoundary.convertToEntity(code));
    }

    /**
     * Delete specific department -- DELETE
     *
     * @param code department's code to delete
     */
    @ApiOperation(
            value = "Delete specific department by department's code"
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.RESOURCE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.RESOURCE_NOT_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @DeleteMapping(path = "/{code}")
    public void deleteDepartmentByCode(@PathVariable("code") int code) {
        this.departmentService.deleteDepartmentByCode(code);
    }

    /**
     * Delete all departments -- DELETE
     */
    @ApiOperation(
            value = "Delete all departments"
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.ALL_RESOURCES_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR),
    })
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        this.departmentService.deleteAll();
    }
}
