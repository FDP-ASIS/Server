package il.ac.afeka.fdp.course.dao;

import il.ac.afeka.fdp.course.data.entity.CourseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseCrud extends MongoRepository<CourseEntity, Long> {
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<CourseEntity> findByNameStartingWith(@Param("name") String name, PageRequest of);

    List<CourseEntity> findAllByLecturers_Id(@Param("lecturers_Id") String id);

    List<CourseEntity> findAllByStudents_Id(@Param("students_Id") String id);

}
