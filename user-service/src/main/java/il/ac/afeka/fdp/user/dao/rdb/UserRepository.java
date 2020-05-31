package il.ac.afeka.fdp.user.dao.rdb;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("dev-edb")
@Repository
public interface UserRepository
//        extends JpaRepository<UserEntity, Long>
{
}
