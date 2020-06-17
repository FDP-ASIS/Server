package il.ac.afeka.fdp.course.data.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareBoundary implements Cloneable {
    private String name;
    private String version;

    @Override
    public SoftwareBoundary clone() throws CloneNotSupportedException {
        super.clone();
        return new SoftwareBoundary(name, version);
    }
}