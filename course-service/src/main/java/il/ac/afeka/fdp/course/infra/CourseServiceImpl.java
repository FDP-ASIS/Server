package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.dao.CourseCrud;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.utils.FinalStrings;
import il.ac.afeka.fdp.course.exceptions.AlreadyExistsException;
import il.ac.afeka.fdp.course.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseCrud courseCrud;

    @Override
    public List<CourseEntity> create(List<CourseEntity> courses) {
        if (courses.stream().anyMatch(entity -> this.courseCrud.existsById(entity.getCode()))) {
            throw new AlreadyExistsException(FinalStrings.COURSE_EXISTS);
        }
        return this.courseCrud.saveAll(courses.stream()
                .peek(courseEntity -> courseEntity.setStudentsIdList(new ArrayList<>()))
                .peek(courseEntity -> courseEntity.setLecturersIdList(new ArrayList<>()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<CourseEntity> getAllCourses(int page, int size, Sort.Direction direction, String sort) {
        return this.courseCrud.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    @Override
    public List<CourseEntity> getCoursesByCourseName(String name, int page, int size, Sort.Direction direction, String sort) {
        return this.courseCrud.findByNameStartingWith(name, PageRequest.of(page, size, direction, sort));
    }

    @Override
    public List<CourseEntity> getCoursesByDepartmentCode(int departmentCode, int page, int size, Sort.Direction direction, String sort) {
        return this.courseCrud.findAllByDepartmentCode(departmentCode, PageRequest.of(page, size, direction, sort));
    }

    @Override
    public CourseEntity getCourseByCode(long code) {
        return this.courseCrud.findById(code).orElseThrow(NotFoundException::new);
    }

    @Override
    public void editCourse(long courseCode, CourseEntity course) {
        Optional<CourseEntity> courseToEditOptional = this.courseCrud.findById(courseCode);
        if (courseToEditOptional.isEmpty()) {
            throw new NotFoundException(FinalStrings.NO_COURSE_FOUND + courseCode);
        }
        CourseEntity courseToEdit = courseToEditOptional.get();
        course.setStudentsIdList(courseToEdit.getStudentsIdList());
        course.setLecturersIdList(courseToEdit.getLecturersIdList());
        if (course.getName().isEmpty())
            course.setName(courseToEdit.getName());
        if (course.getDepartment() == null)
            course.setDepartment(courseToEdit.getDepartment());
        this.courseCrud.save(course);
        if (courseCode != course.getCode())
            this.courseCrud.deleteById(courseCode);
    }

    @Override
    public void deleteCourseByCode(long courseCode) {
        if (!this.courseCrud.existsById(courseCode)) {
            throw new NotFoundException(FinalStrings.NO_COURSE_FOUND + courseCode);
        }
        this.courseCrud.deleteById(courseCode);
    }

    @Override
    public void deleteAll() {
        this.courseCrud.deleteAll();
    }
}