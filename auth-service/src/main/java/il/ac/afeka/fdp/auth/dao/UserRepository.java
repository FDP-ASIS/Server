package il.ac.afeka.fdp.auth.dao;

import il.ac.afeka.fdp.auth.data.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
