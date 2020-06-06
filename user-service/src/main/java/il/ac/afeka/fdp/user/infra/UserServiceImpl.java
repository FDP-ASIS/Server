package il.ac.afeka.fdp.user.infra;

import il.ac.afeka.fdp.user.dao.UserRepository;
import il.ac.afeka.fdp.user.data.UserRoleEnum;
import il.ac.afeka.fdp.user.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
        //TODO add password encryption
        return repository.saveAll(entities.stream()
                .filter(userEntity -> !repository.existsById(userEntity.getId()))
                .filter(userEntity -> !repository.existsByUsername(userEntity.getUsername()))
                .filter(userEntity -> !repository.existsByEmail(userEntity.getEmail()))
                .peek(userEntity -> userEntity.setRole(role))
                .peek(userEntity -> userEntity.setPassword(String.valueOf(userEntity.getId())))
                .collect(Collectors.toList()));
    }

    @Override
    public List<UserEntity> getAllUsers(int page, int size, Sort orders) {
        return null;
    }
}
