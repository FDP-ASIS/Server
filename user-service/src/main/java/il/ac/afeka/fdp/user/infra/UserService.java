package il.ac.afeka.fdp.user.infra;

import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserEntity> signUp(List<UserEntity> entities, UserRoleEnum role);

    List<UserEntity> getAllUsers(int page, int size, Sort orders);
}
