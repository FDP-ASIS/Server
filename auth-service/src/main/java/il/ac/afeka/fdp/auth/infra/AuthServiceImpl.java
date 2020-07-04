package il.ac.afeka.fdp.auth.infra;

import il.ac.afeka.fdp.auth.dao.UserRepository;
import il.ac.afeka.fdp.auth.data.Token;
import il.ac.afeka.fdp.auth.data.UserWithToken;
import il.ac.afeka.fdp.auth.data.boundary.UserBoundary;
import il.ac.afeka.fdp.auth.exceptions.InvalidCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserWithToken login(String username, String password) {
        return new UserWithToken(repository.findByUsernameAndPassword(username, encryptPassword(password)).orElseThrow(InvalidCredentials::new), "");
    }

    @Override
    public void logout() {

    }

    @Override
    public UserBoundary auth(Token token) {
        // TODO implement the function
        return null;
    }

    private String encryptPassword(String password) {
        // TODO change password encryption
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
