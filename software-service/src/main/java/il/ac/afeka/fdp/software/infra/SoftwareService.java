package il.ac.afeka.fdp.software.infra;

import il.ac.afeka.fdp.software.data.Software;

import java.util.List;

public interface SoftwareService {
    Software create (Software software);
    Software getSoftwareByName(String softwareName);
    List<Software> getAllSoftware(int page, int size, String sort);
    List<Software> getSoftwareBySoftwareName(String softwareName, int page, int size, String sort);
    List<Software> getSoftwareByVersion(String version, int page, int size, String sort);
    void editSoftware(String softwareName, Software software);
    void deleteSoftwareByName(String softwareName);
    void deleteAll();
}
