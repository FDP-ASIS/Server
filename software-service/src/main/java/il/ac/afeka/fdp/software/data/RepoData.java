package il.ac.afeka.fdp.software.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoData {
    private String name;
    private String download_url;
}
