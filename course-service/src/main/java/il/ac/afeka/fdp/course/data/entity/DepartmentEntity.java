package il.ac.afeka.fdp.course.data.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@RequiredArgsConstructor(staticName = "of")
//@Document(collection = "DEPARTMENTS")
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {
    @Id
    @NonNull
    @Size(min = 2, max = 2)
    private Integer code;
    @NonNull
    @NotBlank
    private String name;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
}


