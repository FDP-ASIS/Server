package il.ac.afeka.fdp.course.data.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Data
@Document(collection = "DEPARTMENTS")
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {

    @Id
    @NonNull
    @Size(min = 2, max = 2)
    private Integer code;
    private String name;
}
