package il.ac.afeka.fdp.course.data.entity;

import il.ac.afeka.fdp.course.data.Software;
import il.ac.afeka.fdp.course.data.User;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
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
    @DBRef
    private List<User> students; //= new ArrayList<>();
    @DBRef
    private List<User> lecturers;// = new ArrayList<>();
    @DBRef
    private List<Software> software; // = new ArrayList<>();
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
}
