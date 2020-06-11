package il.ac.afeka.fdp.user.dao;

import il.ac.afeka.fdp.user.data.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends MongoRepository<UserEntity, String> {
    boolean existsByIdOrUsernameOrEmail(@Param("id") String id,@Param("username") String username,@Param("email") String email);
}
