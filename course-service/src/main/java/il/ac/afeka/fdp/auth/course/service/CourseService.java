package il.ac.afeka.fdp.auth.course.service;

import il.ac.afeka.fdp.auth.course.model.Course;

import java.util.List;

public interface CourseService {
    Course create (Course course);
    Course getCourseByCode(String courseCode);
    List<Course> getAllCourses(int page, int size, String sort);
    List<Course> getCourseByCourseName(String courseName, int page, int size, String sort);
    List<Course> getCourseByDepartment(String department, int page, int size, String sort);
    List<Course> getCourseBySoftwareList(String softwareList, int page, int size, String sort);
    void editCourse(String courseCode, Course course);
    void deleteCourseByCode(String courseCode);
    void deleteAll();


}
