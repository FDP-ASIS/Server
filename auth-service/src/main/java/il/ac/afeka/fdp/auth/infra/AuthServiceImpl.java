package il.ac.afeka.fdp.auth.infra;

import il.ac.afeka.fdp.auth.dao.UserRepository;
import il.ac.afeka.fdp.auth.data.UserWithToken;
import il.ac.afeka.fdp.auth.data.entity.UserEntity;
import il.ac.afeka.fdp.auth.exception.InvalidCredentials;
import il.ac.afeka.fdp.auth.exception.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    final String FAKE_TOKEN = "FAKE_TOKEN";
    @Autowired
    private UserRepository repository;

    @Override
    public UserWithToken login(String username, String password) {
        return new UserWithToken(repository.findByUsernameAndPassword(username, encryptPassword(password)).orElseThrow(InvalidCredentials::new), FAKE_TOKEN);
    }

    @Override
    public void logout() {

    }

    private String encryptPassword(String password) {
        //TODO add password encryption
        return password;
    }
}
