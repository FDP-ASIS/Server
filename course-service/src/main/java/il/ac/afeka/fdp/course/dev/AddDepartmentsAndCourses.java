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
        try {
            departmentService.create(Arrays.asList(
                    DepartmentEntity.of(10, "SOFTWARE_ENGINEERING"),
                    DepartmentEntity.of(20, "INDUSTRIAL_ENGINEERING_MANAGEMENT"),
                    DepartmentEntity.of(30, "ELECTRICAL_ENGINEERING")
            ));
            courseService.create(Arrays.asList(
                    CourseEntity.of(12345, "course1", DepartmentEntity.of(10, "SOFTWARE_ENGINEERING")),
                    CourseEntity.of(11111, "course2", DepartmentEntity.of(10, "SOFTWARE_ENGINEERING")),
                    CourseEntity.of(22222, "course3", DepartmentEntity.of(20, "SOFTWARE_ENGINEERING"))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
