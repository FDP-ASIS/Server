package il.ac.afeka.fdp.software.controller;

import il.ac.afeka.fdp.software.service.GitService;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class GitController {
    private GitService git;

    @Autowired
    public GitController(GitService git) {
        super();
        this.git= git;
    }

    /**
     * Get names of software-- GET
     */

    @RequestMapping(
            path = "/git/names",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getAllNames() throws JSONException, URISyntaxException {
        return this.git.getAllNames();
    }

    /**
     * Get version of specific software-- GET
     */
    @RequestMapping(
            path = "/git/{softwareName}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getAllVersions(
            @PathVariable("softwareName") String softwareName) throws JSONException, URISyntaxException {
        return this.git.getAllVersions(softwareName);
    }

    /**
     * Get download link of software-- GET
     */
    @RequestMapping(
            path = "/git/install/{softwareName}/{version}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getVersionLinkInstall(
            @PathVariable("softwareName") String softwareName,
            @PathVariable("version") String version) throws JSONException, URISyntaxException {
        return this.git.getVersionLinkInstall(softwareName,version);
    }

    /**
     * Get download link of software-- GET
     */
    @RequestMapping(
            path = "/git/delete/{softwareName}/{version}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> getVersionLinkDelete(
            @PathVariable("softwareName") String softwareName,
            @PathVariable("version") String version) throws JSONException, URISyntaxException {
        return this.git.getVersionLinkDelete(softwareName,version);
    }
}
