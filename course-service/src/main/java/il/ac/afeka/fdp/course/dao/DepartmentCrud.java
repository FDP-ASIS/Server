package il.ac.afeka.fdp.course.dao;

import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Profile("department-dev")
public interface DepartmentCrud extends MongoRepository<DepartmentEntity, Integer> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<DepartmentEntity> findByNameRegex(@Param("name") String name, PageRequest of);

    List<DepartmentEntity> findByCode(@Param("code") int code, PageRequest of);
}
