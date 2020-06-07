package il.ac.afeka.fdp.course.dao;

import il.ac.afeka.fdp.course.data.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseCrud extends MongoRepository<Course, String> {
    boolean existsCourseByCourseCode(String code);
    Course findByCourseCode(@Param("courseCode") String courseCode);
    List<Course> findAllByCourseName(@Param("courseName") String courseName, Pageable pageable);
    List<Course> findAllByDepartment(@Param("department") String department, Pageable pageable);
    List<Course> findAllBySoftwareList(@Param("software") String softwareList, Pageable pageable);

}
