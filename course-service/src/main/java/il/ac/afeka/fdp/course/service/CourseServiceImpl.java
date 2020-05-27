package il.ac.afeka.fdp.course.service;

import il.ac.afeka.fdp.course.crud.CourseCrud;
import il.ac.afeka.fdp.course.exceptions.BadReqException;
import il.ac.afeka.fdp.course.exceptions.CourseAlreadyExistsException;
import il.ac.afeka.fdp.course.exceptions.CourseNotFoundException;
import il.ac.afeka.fdp.course.model.Course;
import il.ac.afeka.fdp.course.utils.FinalStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseCrud courseCrud;

    @Autowired
    public CourseServiceImpl(CourseCrud courseCrud) {
        super();
        this.courseCrud = courseCrud;
    }

    @Override
    public Course create(Course course) {
        String code = course.getCourseCode();
        if (this.courseCrud.existsCourseByCourseCode(code)) {
            throw new CourseAlreadyExistsException(FinalStrings.COURSE_EXISTS + code);
        }
        return this.courseCrud.save(course);
    }

    @Override
    public List<Course> getAllCourses(int page, int size, String sort) {
        return this.courseCrud.findAll(PageRequest.of(page, size, Sort.Direction.ASC, sort)).getContent();
    }

    @Override
    public List<Course> getCourseByCourseName(String name, int page, int size, String sort) {
        return this.courseCrud.findAllByCourseName(name, PageRequest.of(page, size, Sort.Direction.ASC, sort));
    }

    @Override
    public List<Course> getCourseByDepartment(String department, int page, int size, String sort) {
        return this.courseCrud.findAllByDepartment(department, PageRequest.of(page, size, Sort.Direction.ASC, sort));
    }

    @Override
    public List<Course> getCourseBySoftwareList(String softwareList, int page, int size, String sort) {
        return this.courseCrud.findAllBySoftwareList(softwareList, PageRequest.of(page, size, Sort.Direction.ASC, sort));
    }

    @Override
    public Course getCourseByCode(String courseCode) {
        Course courseToReturn = this.courseCrud.findByCourseCode(courseCode);
        if (courseToReturn == null) {
            throw new CourseNotFoundException(FinalStrings.NO_COURSE_FOUND + courseCode);
        }
        return courseToReturn;
    }

    @Override
    public void editCourse(String courseCode, Course course) {
        Course courseToEdit = this.courseCrud.findByCourseCode(courseCode);
        if (courseToEdit == null) {
            throw new CourseNotFoundException(FinalStrings.NO_COURSE_FOUND + courseCode);
        }
        courseToEdit.setCourseCode(course.getCourseCode());
        courseToEdit.setCourseName(course.getCourseName());
        courseToEdit.setDepartment(course.getDepartment());
        courseToEdit.setSoftwareList(course.getSoftwareList());
    }

    @Override
    public void deleteCourseByCode(String courseCode) {
        Course courseToDelete = this.courseCrud.findByCourseCode(courseCode);
        if (courseToDelete == null) {
            throw new CourseNotFoundException(FinalStrings.NO_COURSE_FOUND + courseCode);
        }

        this.courseCrud.delete(courseToDelete);
    }

    @Override
    public void deleteAll() {
        this.courseCrud.deleteAll();
    }
}