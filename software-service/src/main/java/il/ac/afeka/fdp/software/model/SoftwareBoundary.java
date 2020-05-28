package il.ac.afeka.fdp.software.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class SoftwareBoundary {
    private String id;
    @NonNull
    private String softwareName;
    @NonNull
    private String version;

    public SoftwareBoundary() {
    }

    public SoftwareBoundary(String softwareName, String version) {
        super();
        this.softwareName = softwareName;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}