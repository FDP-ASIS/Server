package il.ac.afeka.fdp.user.infra;

import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    List<UserEntity> signUp(List<UserEntity> entities, UserRoleEnum role);
    List<UserEntity> getAllUsers(int page, int size, Sort.Direction direction, String sort);
    UserEntity getUserById(String id);
    void deleteAllUsers();
    void deleteUserById(String id);
    void updateUserById(String id, UserEntity user);
    UserEntity updateUserPasswordById(String id, String oldPassword, String newPassword);
}
