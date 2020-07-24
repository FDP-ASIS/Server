package il.ac.afeka.fdp.course.infra;

import il.ac.afeka.fdp.course.dao.CourseCrud;
import il.ac.afeka.fdp.course.data.Software;
import il.ac.afeka.fdp.course.data.User;
import il.ac.afeka.fdp.course.data.boundary.UserRole;
import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import il.ac.afeka.fdp.course.exceptions.course.CourseAlreadyExistsException;
import il.ac.afeka.fdp.course.exceptions.course.CourseNotFoundException;
import il.ac.afeka.fdp.course.exceptions.root.BadReqException;
import il.ac.afeka.fdp.course.exceptions.root.ConflictException;
import il.ac.afeka.fdp.course.exceptions.root.NotFoundException;
import il.ac.afeka.fdp.course.logger.CoursePerformance;
import il.ac.afeka.fdp.course.logger.Logger;
import il.ac.afeka.fdp.course.utils.SoftwareClient;
import il.ac.afeka.fdp.course.utils.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Course Service
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseCrud courseCrud;

    @Autowired
    private UserClient userClient;
    @Autowired
    private SoftwareClient softwareClient;

    /**
     * @param courses course details
     * @return courses details after creation
     */
    @Override
    @Logger
    @CoursePerformance
    public List<CourseEntity> create(List<CourseEntity> courses) {
        if (courses.stream().anyMatch(entity -> this.courseCrud.existsById(entity.getCode()))) {
            throw new CourseAlreadyExistsException();
        }
        return this.courseCrud.saveAll(courses.stream()
                .peek(courseEntity -> courseEntity.setStudents(new ArrayList<>()))
                .peek(courseEntity -> courseEntity.setLecturers(new ArrayList<>()))
                .peek(courseEntity -> courseEntity.setSoftware(new ArrayList<>()))
                .collect(Collectors.toList()));
    }

    /**
     * @param page page
     * @param size size
     * @param direction ASC/DESC
     * @param sort sort type
     * @return All courses details in the system
     */
    @Override
    @Logger
    @CoursePerformance
    public List<CourseEntity> getAllCourses(int page, int size, Sort.Direction direction, String sort) {
        return this.courseCrud.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    /**
     * @param name course name
     * @param page page
     * @param size size
     * @param direction ASC/DESC
     * @param sort sort type
     * @return get courses details by name
     */
    @Override
    @Logger
    @CoursePerformance
    public List<CourseEntity> getCoursesByName(String name, int page, int size, Sort.Direction direction, String sort) {
        return this.courseCrud.findByNameStartingWith(name, PageRequest.of(page, size, direction, sort));
    }

    /**
     * @param code course code
     * @return get course details by code
     */
    @Override
    @Logger
    @CoursePerformance
    public CourseEntity getCourseByCode(long code) {
        return this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
    }

    /**
     * Edit course details by code
     * @param code course code
     * @param course course details
     */
    @Override
    @Logger
    @CoursePerformance
    public void editCourse(long code, CourseEntity course) {
        CourseEntity courseToEdit = this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        course.setStudents(courseToEdit.getStudents());
        course.setLecturers(courseToEdit.getLecturers());
        course.setSoftware(courseToEdit.getSoftware());
        if (course.getName().isEmpty())
            course.setName(courseToEdit.getName());
        this.courseCrud.save(course);
        if (code != course.getCode())
            this.courseCrud.deleteById(code);
    }

    /**
     * Delete specific course by code
     * @param courseCode course code
     */
    @Override
    @Logger
    @CoursePerformance
    public void deleteCourseByCode(long courseCode) {
        if (!this.courseCrud.existsById(courseCode)) {
            throw new CourseNotFoundException(courseCode);
        }
        this.courseCrud.deleteById(courseCode);
    }

    /**
     * Delete all courses from the system
     */
    @Override
    @Logger
    @CoursePerformance
    public void deleteAll() {
        this.courseCrud.deleteAll();
    }

    /**
     * @param code course code
     * @param id user id
     * @param role user role
     * @return course details after assign user
     */
    @Override
    @Logger
    @CoursePerformance
    public CourseEntity assign(long code, String id, UserRole role) {
        CourseEntity entity = this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        User user;
        try {
            user = userClient.getUser(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
        switch (role) {
            case STUDENT:
                if (entity.getStudents().stream().anyMatch((u -> u.getId().equals(id))))
                    throw new ConflictException();
                entity.getStudents().add(user);
                break;
            case LECTURER:
                if (entity.getLecturers().stream().anyMatch((u -> u.getId().equals(id))))
                    throw new ConflictException();
                entity.getLecturers().add(user);
                break;
            default:
                throw new BadReqException("Role not found");
        }
        return this.courseCrud.save(entity);
    }

    /**
     * Remove user from course
     * @param code course code
     * @param id user id
     * @param role user role
     */
    @Override
    @Logger
    @CoursePerformance
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

    /**
     * @param id user id
     * @param role user role
     * @return courses details by user id
     */
    @Override
    @Logger
    @CoursePerformance
    public List<CourseEntity> findMyCourses(String id, UserRole role) {
        List<CourseEntity> rv;
        switch (role) {
            case LECTURER:
                rv = this.courseCrud.findAllByLecturers_Id(id);
                break;
            case STUDENT:
                rv = this.courseCrud.findAllByStudents_Id(id);
                break;
            default:
                throw new BadReqException();
        }
        return rv.stream().peek(courseEntity -> {
            courseEntity.setLecturers(Collections.emptyList());
            courseEntity.setStudents(Collections.emptyList());
        }).collect(Collectors.toList());
    }

    /**
     * @param code course code
     * @param softwareId softwareId
     * @return course details after adding a software to it
     */
    @Override
    @Logger
    @CoursePerformance
    public CourseEntity addSoftware(Long code, String softwareId) {
        Software software = this.softwareClient.getSoftware(softwareId);
        if (software == null)
            throw new NotFoundException();
        CourseEntity entity = this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        if (entity.getSoftware().stream().anyMatch(software1 -> software1.getId().equals(softwareId))) {
            throw new ConflictException();
        }
        entity.getSoftware().add(software);
        return this.courseCrud.save(entity);
    }

    /**
     * Remove software from a course
     * @param code code course
     * @param softwareId softwareId
     */
    @Override
    @Logger
    @CoursePerformance
    public void removeSoftware(Long code, String softwareId) {
        if (this.softwareClient.getSoftware(softwareId) == null)
            throw new NotFoundException();
        CourseEntity entity = this.courseCrud.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
        entity.getSoftware().removeIf(software -> software.getId().equals(softwareId));
        this.courseCrud.save(entity);
    }
}