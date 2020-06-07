package il.ac.afeka.fdp.software.controller;

import il.ac.afeka.fdp.software.model.Software;
import il.ac.afeka.fdp.software.model.SoftwareBoundary;
import il.ac.afeka.fdp.software.exceptions.BadReqException;
import il.ac.afeka.fdp.software.service.SoftwareService;
import il.ac.afeka.fdp.software.utils.FinalStrings;
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
public class SoftwareController {
    private SoftwareService software;

    @Autowired
    public SoftwareController(SoftwareService software) {
        super();
        this.software = software;
    }

    /**
     * Create a new software -- POST
     */
    @ApiOperation(
            value = "Create a new software",
            notes = "Adds a software to the system\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = FinalStrings.SOFTWARE_CREATED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.CREATE_BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_CONFLICT, message = FinalStrings.SOFTWARE_EXISTS),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR) })

    @RequestMapping(
            path = "/software",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public SoftwareBoundary create(@RequestBody SoftwareBoundary newSoftware) {
        return fromEntity(this.software.create(toEntity(newSoftware)));
    }

    /**
     * Get All Software -- GET
     */
    @ApiOperation(
            value = "Get all software",
            notes = "Get all software from the database")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, response = SoftwareBoundary[].class, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/software",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SoftwareBoundary[] getAllSoftwareByFilter(
            @RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
            @RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Software> rv = null;
        if (filterType.isEmpty()) {
            rv = this.software.getAllSoftware(page, size, sort);
        } else {
            try {
                if (filterValue.isEmpty())
                    throw new RuntimeException("Value to search is empty");
                switch (filterType) {
                    case "softwareName":
                        rv = this.software.getSoftwareBySoftwareName(filterValue, page, size, sort);
                        break;

                    case "version":
                        rv = this.software.getSoftwareByVersion(filterValue, page, size, sort);
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
                .toArray(new SoftwareBoundary[0]);
    }

    /**
     * Get software by software name -- GET
     */
    @ApiOperation(
            value = "Get software by its software name",
            notes = "Get this software from the database")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, response = SoftwareBoundary.class, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NO_SOFTWARE_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/software/{softwareName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public SoftwareBoundary getSoftwareByName(
            @PathVariable("softwareName") String softwareName) {
        return fromEntity(this.software.getSoftwareByName(softwareName));
    }

    /**
     * Edit specific software -- PUT
     */
    @ApiOperation(
            value = "Edit software details by software name",
            notes = "Edit this software in database.\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.SPECIFIC_SOFTWARE_EDITED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NO_SOFTWARE_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/software/{softwareName}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void editSoftware(
            @PathVariable String softwareName,
            @RequestBody SoftwareBoundary softwareEdit) {
        this.software.editSoftware(softwareName,toEntity(softwareEdit));
    }

    /**
     * Delete specific software -- DELETE
     */
    @ApiOperation(
            value = "Delete specific software by software name",
            notes = "Remove this software from the database.\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.SPECIFIC_SOFTWARE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_INPUT),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = FinalStrings.NO_SOFTWARE_FOUND),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/software/{softwareName}",
            method = RequestMethod.DELETE)
    public void deleteSoftwareByName(
            @PathVariable("softwareName") String softwareName) {
        this.software.deleteSoftwareByName(softwareName);
    }

    /**
     * Delete all software -- DELETE
     */
    @ApiOperation(
            value = "Delete all software",
            notes = "Delete software from the database.\n" +
                    "Can be called only by Admin/Lecturer after authentication")

    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = FinalStrings.SOFTWARE_DELETED),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)})

    @RequestMapping(
            path = "/software",
            method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        this.software.deleteAll();
    }

    /**
     * Transform from database object entity to user object boundary
     * @param software entity object
     * @return SoftwareBoundary object
     */
    private SoftwareBoundary fromEntity(Software software) {
        SoftwareBoundary rv = new SoftwareBoundary();
        rv.setId(software.getId());
        rv.setSoftwareName(software.getSoftwareName());
        rv.setVersion(software.getVersion());
        return rv;
    }

    /**
     * Transform from user object boundary to database object entity
     * @param software boundary object
     * @return software entity object
     */
    private Software toEntity(SoftwareBoundary software) {
        try {
            Software rv = new Software();
            rv.setSoftwareName(software.getSoftwareName());
            rv.setVersion(software.getVersion());
            return rv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}