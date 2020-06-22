package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.data.boundary.UserRole;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CourseService {
    List<CourseEntity> create(List<CourseEntity> courses);
    CourseEntity getCourseByCode(long code);
    List<CourseEntity> getAllCourses(int page, int size, Sort.Direction direction, String sort);
    List<CourseEntity> getCoursesByCourseName(String name, int page, int size, Sort.Direction direction, String sort);
    List<CourseEntity> getCoursesByDepartmentCode(int departmentCode, int page, int size, Sort.Direction direction, String sort);
    void editCourse(long courseCode, CourseEntity course);
    void deleteCourseByCode(long courseCode);
    void deleteAll();
    void assign(long code, String id, UserRole role);
}
