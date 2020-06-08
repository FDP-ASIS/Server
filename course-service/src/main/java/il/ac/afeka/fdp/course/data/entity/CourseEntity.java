package il.ac.afeka.fdp.course.data.entity;

import il.ac.afeka.fdp.course.data.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@RequiredArgsConstructor(staticName = "of")
@Document(collection = "COURSES")
@Validated
public class CourseEntity {
    @Id
    @Size(min = 5, max = 5)
    private final long code;
    @NonNull
    private String name;
    @NonNull
    @DBRef
    private DepartmentEntity department;
//    @NonNull
//    @DBRef
//    private List<String> softwareIdList;
    @NonNull
    @DBRef
    private List<User> studentsIdList;
    @NonNull
    @DBRef
    private List<User> lecturersIdList;
}
