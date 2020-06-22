package il.ac.afeka.fdp.course.data.entity;

import il.ac.afeka.fdp.course.data.User;
import il.ac.afeka.fdp.course.data.boundary.SoftwareBoundary;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
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
    @NotBlank
    private String name;
    @NonNull
    @DBRef
    @NotBlank
    private DepartmentEntity department;
    @DBRef
    private List<User> studentsIdList; //= new ArrayList<>();
    @DBRef
    private List<User> lecturersIdList;// = new ArrayList<>();
    @DBRef
    private List<SoftwareBoundary> softwareDetails; // = new ArrayList<>();
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
}
