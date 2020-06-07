package il.ac.afeka.fdp.auth.infra;

import il.ac.afeka.fdp.auth.dao.UserRepository;
import il.ac.afeka.fdp.auth.data.entity.UserEntity;
import il.ac.afeka.fdp.auth.exception.InvalidCredentials;
import il.ac.afeka.fdp.auth.exception.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserEntity login(String username, String password) {
        return repository.findByUsernameAndPassword(username, encryptPassword(password)).orElseThrow(InvalidCredentials::new);
    }

    @Override
    public void logout() {

    }

    private String encryptPassword(String password) {
        //TODO add password encryption
        return password;
    }
}
