package il.ac.afeka.fdp.user.infra;

import il.ac.afeka.fdp.user.dao.UserRepository;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import il.ac.afeka.fdp.user.exception.IdMustBeOnlyDigits;
import il.ac.afeka.fdp.user.exception.UserAlreadyExists;
import il.ac.afeka.fdp.user.exception.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<UserEntity> signUp(List<UserEntity> entities, UserRoleEnum role) {
        return repository.saveAll(entities.stream()
                .peek(userEntity -> {
                    try {
                        Long.valueOf(userEntity.getId());
                    } catch (NumberFormatException e) {
                        throw new IdMustBeOnlyDigits();
                    }
                }).peek(userEntity -> {
                    if (repository.existsByIdOrUsernameOrEmail(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail()))
                        throw new UserAlreadyExists();
                })
                .peek(userEntity -> userEntity.setRole(role))
                .peek(userEntity -> userEntity.setPassword(encryptPassword(String.valueOf(userEntity.getId()))))
                .collect(Collectors.toList()));
    }

    @Override
    public List<UserEntity> getAllUsers(int page, int size, Sort.Direction direction, String sort) {
        return this.repository.findAll(PageRequest.of(page, size, direction, sort)).getContent();
    }

    @Override
    public UserEntity getUserById(String id) {
        return this.repository.findById(id).orElseThrow(UserNotFound::new);
    }

    @Override
    public void deleteAllUsers() {
        this.repository.deleteAll();
    }

    @Override
    public void deleteUserById(String id) {
        if (!this.repository.existsById(id)) {
            throw new UserNotFound();
        }
        this.repository.deleteById(id);
    }

    @Override
    public void updateUserById(UserEntity user) {
        if (!this.repository.existsById(user.getId()))
            throw new UserNotFound();
        this.repository.save(user);

    }

    @Override
    public UserEntity updateUserPasswordById(String id, String password) {
        UserEntity userEntity = this.repository.findById(id).orElseThrow(UserNotFound::new);
        userEntity.setPassword(encryptPassword(password));
        return this.repository.save(userEntity);
    }

    private String encryptPassword(String password) {
        //TODO add password encryption
        return password;
    }
}
