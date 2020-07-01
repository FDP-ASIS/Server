package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.ScriptType;
import il.ac.afeka.fdp.software.data.Software;

import java.util.List;

public interface SoftwareService {

    List<String> getAllSoftware();

    List<Software> getSoftwareVersions(String name);

    String getScriptURL(String name, String version, ScriptType type);

    Software getSoftware(String id);
}
