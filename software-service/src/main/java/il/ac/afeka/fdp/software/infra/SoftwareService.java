package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.ScriptType;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SoftwareService {
    List<String> getAllSoftware(int page, int size, Sort.Direction direction);

    List<String> getSoftwareVersions(String name, int page, int size);

    String getScriptURL(String name, String version, ScriptType type);
}
