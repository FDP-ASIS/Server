package il.ac.afeka.fdp.course.dao;

import il.ac.afeka.fdp.course.data.entity.DepartmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentCrud extends MongoRepository<DepartmentEntity, Integer> {

}
