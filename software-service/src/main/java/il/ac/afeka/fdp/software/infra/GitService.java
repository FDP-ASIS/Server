package il.ac.afeka.fdp.software.infra;

import org.json.JSONException;

import java.net.URISyntaxException;
import java.util.List;

public interface GitService {
    List<String> getAllNames() throws JSONException, URISyntaxException;
    List<String> getAllVersions(String softwareName) throws JSONException, URISyntaxException;
    List<String> getVersionLinkInstall(String softwareName, String version) throws JSONException, URISyntaxException;
    List<String> getVersionLinkDelete(String softwareName, String version) throws JSONException, URISyntaxException;
}
