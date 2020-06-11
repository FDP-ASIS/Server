package il.ac.afeka.fdp.course.dao;

import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseCrud extends MongoRepository<CourseEntity, Long> {

    List<CourseEntity> findByNameStartingWith(@Param("name") String name, PageRequest of);

    List<CourseEntity> findAllByDepartmentCode(@Param("departmentCode") int code, PageRequest of);

}
