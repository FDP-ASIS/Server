package il.ac.afeka.fdp.software.infra;

import java.util.List;

public interface SoftwareBatchService {
    List<String> getAllSoftware();
    List<String> getSoftwareVersions(String name);
}
