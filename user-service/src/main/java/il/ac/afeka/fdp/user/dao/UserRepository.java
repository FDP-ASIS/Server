package il.ac.afeka.fdp.user.dao;

import il.ac.afeka.fdp.user.data.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, Long> {

}
