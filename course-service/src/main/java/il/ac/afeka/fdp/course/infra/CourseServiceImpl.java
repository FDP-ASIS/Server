package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.dao.CourseCrud;
import il.ac.afeka.fdp.course.data.User;
import il.ac.afeka.fdp.course.data.boundary.UserRole;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.exceptions.course.CourseAlreadyExistsException;
import il.ac.afeka.fdp.course.exceptions.course.CourseNotFoundException;
import il.ac.afeka.fdp.course.exceptions.root.BadReqException;
import il.ac.afeka.fdp.course.exceptions.root.ConflictException;
import il.ac.afeka.fdp.course.exceptions.root.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseCrud courseCrud;

//    @Autowired
//    private DepartmentService departmentService;

    @Override
    public List<CourseEntity> create(List<CourseEntity> courses) {
        if (courses.stream().anyMatch(entity -> this.courseCrud.existsById(entity.getCode()))) {
            throw new CourseAlreadyExistsException();
        }
        return this.courseCrud.saveAll(courses.stream()
//                .peek(courseEntity -> courseEntity.setDepartment(departmentService.getDepartmentByCode(courseEntity.getDepartment().getCode())))
                .peek(courseEntity -> courseEntity.setStudents(new ArrayList<>()))
                .peek(courseEntity -> courseEntity.setLecturers(new ArrayList<>()))
                .peek(courseEntity -> courseEntity.setSoftware(new ArrayList<>()))
//                .peek(courseEntity -> courseEntity.setCreatedDate(new Date()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<CourseEntity> getAllCourses(int page, int size, Sort.Direction direction, String sort) {
        return this.courseCrud.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    @Override
    public List<CourseEntity> getCoursesByName(String name, int page, int size, Sort.Direction direction, String sort) {
        return this.courseCrud.findByNameStartingWith(name, PageRequest.of(page, size, direction, sort));
    }

//    @Override
//    public List<CourseEntity> getCoursesByDepartmentCode(int departmentCode, int page, int size, Sort.Direction direction, String sort) {
//        return this.courseCrud.findAllByDepartmentCode(departmentCode, PageRequest.of(page, size, direction, sort));
//    }

    @Override
    public CourseEntity getCourseByCode(long code) {
        return this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
    }

    @Override
    public void editCourse(long code, CourseEntity course) {
        CourseEntity courseToEdit = this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        course.setStudents(courseToEdit.getStudents());
        course.setLecturers(courseToEdit.getLecturers());
        course.setSoftware(courseToEdit.getSoftware());
        if (course.getName().isEmpty())
            course.setName(courseToEdit.getName());
//        if (course.getDepartment() == null)
//            course.setDepartment(courseToEdit.getDepartment());
        this.courseCrud.save(course);
        if (code != course.getCode())
            this.courseCrud.deleteById(code);
    }

    @Override
    public void deleteCourseByCode(long courseCode) {
        if (!this.courseCrud.existsById(courseCode)) {
            throw new CourseNotFoundException(courseCode);
        }
        this.courseCrud.deleteById(courseCode);
    }

    @Override
    public void deleteAll() {
        this.courseCrud.deleteAll();
    }

    @Override
    public CourseEntity assign(long code, String id, UserRole role) {
        CourseEntity entity = this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        //TODO check if user exists in user service
        switch (role) {
            case STUDENT:
                if (entity.getStudents().stream().anyMatch((user -> user.getId().equals(id))))
                    throw new ConflictException();
                entity.getStudents().add(User.of(id));
                break;
            case LECTURER:
                if (entity.getLecturers().stream().anyMatch((user -> user.getId().equals(id))))
                    throw new ConflictException();
                entity.getLecturers().add(User.of(id));
                break;
            default:
                throw new BadReqException("Role not found");
        }
        return this.courseCrud.save(entity);
    }

    @Override
    public void remove(long code, String id, UserRole role) {
        CourseEntity entity = this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        List<User> list;
        switch (role) {
            case STUDENT:
                list = entity.getStudents();
                break;
            case LECTURER:
                list = entity.getLecturers();
                break;
            default:
                throw new BadReqException("Role not found");
        }
        if (list.stream().noneMatch(user -> user.getId().equals((id))))
            throw new NotFoundException("User with id: " + id + " not found in this course with role " + role.name());
        list.removeIf(user -> user.getId().equals((id)));
        this.courseCrud.save(entity);
    }
}