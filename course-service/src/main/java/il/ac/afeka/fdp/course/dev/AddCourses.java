package il.ac.afeka.fdp.course.dev;

import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.infra.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
public class AddCourses implements CommandLineRunner {

    @Autowired
    private CourseService courseService;

    @Override
    public void run(String... args) throws Exception {
        try {
            courseService.create(Arrays.asList(
                    CourseEntity.of(10806, "תכנות ותיכון מונחה עצמים"),
                    CourseEntity.of(10805, "מבני נתונים ואלגוריתמים"),
                    CourseEntity.of(10824, "מבוא לתכנות"),

                    CourseEntity.of(10006, "מבוא למדעי המחשב"),
                    CourseEntity.of(10118, "תכנות מונחה עצמים"),
                    CourseEntity.of(10119, "תיכון מונחה עצמים")
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
