package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.RepoData;
import il.ac.afeka.fdp.software.data.ScriptType;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SoftwareService {
    List<String> getAllSoftware(int page, int size, Sort.Direction direction);
    List<String> getVersionsOfSoftware(String softwareName);
    List<String> getSoftwareByNameStartingWith(String softwareName);
    String getScriptURL(String softwareName, String version, ScriptType scriptType);
}
