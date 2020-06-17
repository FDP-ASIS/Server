package il.ac.afeka.fdp.course.data.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareBoundary {
    private String name;
    private String version;
}