package il.ac.afeka.fdp.course.data.boundary;

import il.ac.afeka.fdp.course.data.Software;
import il.ac.afeka.fdp.course.data.User;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseBoundary {
    private Long code;
    private String name;
    private List<User> students;
    private List<User> lecturers;
    private List<Software> software;

    public CourseBoundary(CourseEntity courseEntity) {
        this.code = courseEntity.getCode();
        this.name = courseEntity.getName();
        this.students = courseEntity.getStudents();
        this.lecturers = courseEntity.getLecturers();
        this.software = courseEntity.getSoftware();
    }

    public CourseEntity convertToEntity() {
        return CourseEntity.of(code, name);
    }

    public CourseEntity convertToEntity(long code) {
        return CourseEntity.of(code, name);
    }
}
