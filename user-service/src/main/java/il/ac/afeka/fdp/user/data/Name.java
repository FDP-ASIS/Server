package il.ac.afeka.fdp.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Name implements Cloneable {
    private String first;
    private String middle;
    private String last;

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Name(first, middle, last);
    }
}
