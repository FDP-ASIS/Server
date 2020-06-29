package il.ac.afeka.fdp.software.layout;

import io.swagger.annotations.Api;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/software/admin")
@Api(value = "Admin Software controller")
public class AdminSoftwareController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobProcess;

    @GetMapping(value = "/load")
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(jobProcess, jobParameters);

        System.out.println("JobExecution: " + jobExecution.getStatus());

        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

        return jobExecution.getStatus();
    }

//
//    @ApiOperation(value = "Get all the software in the system", nickname = "allSoftware")
//    @ApiResponses(value = {
//            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
//            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
//            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
//            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
//    })
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    String[] getAllSoftware(
//            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
//            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction) {
//        return softwareService.getAllSoftware(page, size, direction).toArray(String[]::new);
//    }
//
//
//    @ApiOperation(value = "Get software's versions", nickname = "softwareVersions")
//    @ApiResponses(value = {
//            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
//            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
//            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
//            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
//    })
//    @GetMapping(
//            path = "/{name}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    String[] getSoftwareVersions(
//            @PathVariable(value = "name") String name,
//            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
//            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
//        return softwareService.getSoftwareVersions(name, page, size).toArray(String[]::new);
//    }
//
//    @ApiOperation(value = "Get script", nickname = "getScript")
//    @ApiResponses(value = {
//            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = FinalStrings.OK),
//            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = FinalStrings.BAD_REQUEST),
//            @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = FinalStrings.UNAUTHORIZED),
//            @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = FinalStrings.SERVER_ERROR)
//    })
//    @GetMapping(
//            path = "/{name}/{version}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    RepoData getSoftwareVersions(
//            @PathVariable(value = "name") String name,
//            @PathVariable(value = "version") String version,
//            @RequestParam(value = "type") ScriptType type) {
//        return new RepoData(null, softwareService.getScriptURL(name, version, type));
//    }
}
