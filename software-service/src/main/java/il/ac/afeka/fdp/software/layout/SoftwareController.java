package il.ac.afeka.fdp.software.layout;

import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.infra.SoftwareService;
import il.ac.afeka.fdp.software.utils.FinalStrings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/software")
@Api(value = "Software controller")
public class SoftwareController {


    @Autowired
    private SoftwareService userService;


    @ApiOperation(value = "Get all the software in the system", nickname = "allSoftware")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    String[] getAllSoftware(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction) {
        return userService.getAllSoftware(page, size, direction).toArray(String[]::new);
    }


    @ApiOperation(value = "Get software's versions", nickname = "softwareVersions")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            path = "/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    String[] getSoftwareVersions(
            @PathVariable(value = "name") String name,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return userService.getSoftwareVersions(name, page, size).toArray(String[]::new);
    }

    @ApiOperation(value = "Get script", nickname = "getScript")
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
    })
    @GetMapping(
            path = "/{name}/{version}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    RepoData getSoftwareVersions(
            @PathVariable(value = "name") String name,
            @PathVariable(value = "version") String version,
            @RequestParam(value = "type") ScriptType type) {
        return new RepoData(null, userService.getScriptURL(name, version, type));
    }
}
