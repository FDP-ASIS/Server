package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.ScriptType;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SoftwareService {

    List<String> getAllSoftware();

    List<String> getSoftwareVersions(String name);

    String getScriptURL(String name, String version, ScriptType type);
}
