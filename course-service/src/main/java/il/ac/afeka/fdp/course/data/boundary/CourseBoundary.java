package il.ac.afeka.fdp.course.data.boundary;

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
//    private DepartmentBoundary department;
    private List<User> studentsIdList;
    private List<User> lecturersIdList;
    private List<SoftwareBoundary> softwareIdList;

    public CourseBoundary(CourseEntity courseEntity) {
        this.code = courseEntity.getCode();
        this.name = courseEntity.getName();
//        this.department = new DepartmentBoundary(courseEntity.getDepartment());
        this.studentsIdList = courseEntity.getStudentsIdList();
        this.lecturersIdList = courseEntity.getLecturersIdList();
        this.softwareIdList = courseEntity.getSoftwareDetails();
    }

    public CourseEntity convertToEntity() {
        return CourseEntity.of(code, name
//                , department.convertToEntity()
        );
    }

    public CourseEntity convertToEntity(long code) {
        return CourseEntity.of(code, name
//                , department.convertToEntity()
        );
    }
}
