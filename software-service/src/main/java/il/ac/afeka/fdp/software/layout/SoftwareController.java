package il.ac.afeka.fdp.software.layout;

import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.data.Software;
import il.ac.afeka.fdp.software.infra.SoftwareService;
import il.ac.afeka.fdp.software.utils.FinalStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

/**
 * Software Controller methods
 */
@RestController
@RequestMapping("/software")
@Api(value = "Software controller")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    /**
     * Get all the names of the software from the database -- GET
     * @return Array of names of the software
     */
    @ApiOperation(value = "Get all the names of the software from the database", nickname = "allSoftware")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    String[] getAllSoftware() {
        return softwareService.getAllSoftware().toArray(String[]::new);
    }

    /**
     * Get all specific software versions -- GET
     * @param name name of the software
     * @return specific software versions by this name
     */
    @ApiOperation(value = "Get all specific software versions",
                  nickname = "softwareVersions")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            path = "{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    Software[] getSoftwareVersions(
            @PathVariable(value = "name") String name) {
        return softwareService.getSoftwareVersions(name).toArray(Software[]::new);
    }

    /**
     * Get the download link of the software script -- GET
     * @param name name of the software
     * @param version version of the software
     * @param type Installation/Deletion
     * @return Url link to download the script file
     */
    @ApiOperation(value = "Get the download link of the software script",
                  nickname = "getScript")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            path = "/{name}/{version}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    RepoData getSoftwareURL(
            @PathVariable(value = "name") String name,
            @PathVariable(value = "version") String version,
            @RequestParam(value = "type") ScriptType type) {
        return new RepoData(null, softwareService.getScriptURL(name, version, type));
    }

    /**
     * Get software by its id -- GET
     * @param id id of the software
     * @return software details
     */
    @ApiOperation(value = "Get software by its id")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = FinalStrings.FORBIDDEN),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            path = "id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    Software getSoftwareById(
            @PathVariable(value = "id") String id) {
        return softwareService.getSoftware(id);
    }
}
