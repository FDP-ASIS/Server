package il.ac.afeka.fdp.user.infra;

import il.ac.afeka.fdp.user.dao.UserDao;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import il.ac.afeka.fdp.user.exception.user.IdMustBeOnlyDigits;
import il.ac.afeka.fdp.user.exception.user.PasswordNotEqual;
import il.ac.afeka.fdp.user.exception.user.UserAlreadyExists;
import il.ac.afeka.fdp.user.exception.user.UserNotFound;
import il.ac.afeka.fdp.user.logger.Logger;
import il.ac.afeka.fdp.user.logger.UserPerformance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User Service
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * @param entities user details
     * @param role user role
     * @return Registered Users details
     */
    @Override
    @Logger
    @UserPerformance
    public List<UserEntity> signUp(List<UserEntity> entities, UserRoleEnum role) {
        return userDao.saveAll(entities.stream()
                .peek(userEntity -> {
                    try {
                        Long.valueOf(userEntity.getId());
                    } catch (NumberFormatException e) {
                        throw new IdMustBeOnlyDigits(userEntity.getId());
                    }
                }).peek(userEntity -> {
                    if (userDao.existsByIdOrUsernameOrEmail(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail()))
                        throw new UserAlreadyExists(userEntity.getId());
                })
                .peek(userEntity -> userEntity.setRole(role))
                .peek(userEntity -> userEntity.setPassword(encryptPassword(String.valueOf(userEntity.getId()))))
                .collect(Collectors.toList()));
    }

    /**
     * @param page page
     * @param size size
     * @param direction ASC/DESC
     * @param sort sort type
     * @return All users in the system
     */
    @Override
    @Logger
    @UserPerformance
    public List<UserEntity> getAllUsers(int page, int size, Sort.Direction direction, String sort) {
        return this.userDao.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    /**
     * @param id user id
     * @return specific user by id
     */
    @Override
    @Logger
    @UserPerformance
    public UserEntity getUserById(String id) {
        return this.userDao.findById(id).orElseThrow(() -> new UserNotFound(id));
    }

    /**
     * Delete all users from the system
     */
    @Override
    @Logger
    @UserPerformance
    public void deleteAllUsers() {
        this.userDao.deleteAll();
    }

    /**
     * Delete specific user by id from the system
     * @param id user id
     */
    @Override
    @Logger
    @UserPerformance
    public void deleteUserById(String id) {
        if (!this.userDao.existsById(id)) {
            throw new UserNotFound(id);
        }
        this.userDao.deleteById(id);
    }

    /**
     * Update user details by id
     * @param id user id
     * @param user user details
     */
    @Override
    @Logger
    @UserPerformance
    public void updateUserById(String id, UserEntity user) {
        UserEntity userToEdit = this.userDao.findById(id).orElseThrow(() -> new UserNotFound(id));
        userToEdit.setUsername(user.getUsername());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setName(user.getName());
        this.userDao.save(userToEdit);
    }

    /**
     * @param id user id
     * @param oldPassword user old password
     * @param newPassword user new password
     * @return user details after changing password
     */
    @Override
    @Logger
    @UserPerformance
    public UserEntity updateUserPasswordById(String id, String oldPassword, String newPassword) {
        UserEntity userEntity = this.userDao.findById(id).orElseThrow(() -> new UserNotFound(id));
        if (!userEntity.getPassword().equals(encryptPassword(oldPassword)   ))
            throw new PasswordNotEqual();
        userEntity.setPassword(encryptPassword(newPassword));
        return this.userDao.save(userEntity);
    }

    /**
     * @param password password
     * @return encrypted password
     */
    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
