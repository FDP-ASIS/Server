package il.ac.afeka.fdp.user.infra;

import il.ac.afeka.fdp.user.dao.UserDao;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import il.ac.afeka.fdp.user.exception.user.IdMustBeOnlyDigits;
import il.ac.afeka.fdp.user.exception.user.UserAlreadyExists;
import il.ac.afeka.fdp.user.exception.user.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
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
//                .peek(userEntity -> userEntity.setCreatedDate(new Date()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<UserEntity> getAllUsers(int page, int size, Sort.Direction direction, String sort) {
        return this.userDao.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    @Override
    public UserEntity getUserById(String id) {
        return this.userDao.findById(id).orElseThrow(() -> new UserNotFound(id));
    }

    @Override
    public void deleteAllUsers() {
        this.userDao.deleteAll();
    }

    @Override
    public void deleteUserById(String id) {
        if (!this.userDao.existsById(id)) {
            throw new UserNotFound(id);
        }
        this.userDao.deleteById(id);
    }

    @Override
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
    public UserEntity updateUserPasswordById(String id, String password) {
        UserEntity userEntity = this.userDao.findById(id).orElseThrow(() -> new UserNotFound(id));
        userEntity.setPassword(encryptPassword(password));
        return this.userDao.save(userEntity);
    }

    private String encryptPassword(String password) {
        //TODO add password encryption
        return password;
    }
}
