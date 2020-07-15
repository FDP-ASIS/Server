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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

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
              //  .peek(userEntity -> userEntity.setCreatedDate(new InstantDate()))
                .collect(Collectors.toList()));
    }

    @Override
    @Logger
    @UserPerformance
    public List<UserEntity> getAllUsers(int page, int size, Sort.Direction direction, String sort) {
        return this.userDao.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    @Override
    @Logger
    @UserPerformance
    public UserEntity getUserById(String id) {
        return this.userDao.findById(id).orElseThrow(() -> new UserNotFound(id));
    }

    @Override
    @Logger
    @UserPerformance
    public void deleteAllUsers() {
        this.userDao.deleteAll();
    }

    @Override
    @Logger
    @UserPerformance
    public void deleteUserById(String id) {
        if (!this.userDao.existsById(id)) {
            throw new UserNotFound(id);
        }
        this.userDao.deleteById(id);
    }

    @Override
    @Logger
    @UserPerformance
    public void updateUserById(String id, UserEntity user) {
        /*if (!this.userDao.existsById(user.getId()))
            throw new UserNotFound(user.getId());
        this.userDao.save(user); */

        UserEntity userToEdit = this.userDao.findById(id).orElseThrow(() -> new UserNotFound(id));
        userToEdit.setUsername(user.getUsername());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setName(user.getName());
        this.userDao.save(userToEdit);
    }

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

    private String encryptPassword(String password) {
        // TODO change password encryption
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
