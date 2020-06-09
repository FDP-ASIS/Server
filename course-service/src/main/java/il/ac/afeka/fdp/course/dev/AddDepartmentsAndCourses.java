package il.ac.afeka.fdp.course.dev;

import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import il.ac.afeka.fdp.course.infra.CourseService;
import il.ac.afeka.fdp.course.infra.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
public class AddDepartmentsAndCourses implements CommandLineRunner {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

    @Override
    public void run(String... args) throws Exception {
        departmentService.create(Arrays.asList(
                new DepartmentEntity(10, "SOFTWARE_ENGINEER"),
                new DepartmentEntity(20, "INDUSTRIAL_ENGINEERING_MANAGEMENT"),
                new DepartmentEntity(30, "ELECTRICAL_ENGINEER")
        ));
        courseService.create(Arrays.asList(
                CourseEntity.of(12345, "course1", new DepartmentEntity(10, null)),
                CourseEntity.of(11111, "course2", new DepartmentEntity(10, null)),
                CourseEntity.of(22222, "course3", new DepartmentEntity(20, null))
        ));
    }
}
