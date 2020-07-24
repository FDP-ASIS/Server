package il.ac.afeka.fdp.auth.infra;

import il.ac.afeka.fdp.auth.dao.UserRepository;
import il.ac.afeka.fdp.auth.data.Token;
import il.ac.afeka.fdp.auth.data.UserWithToken;
import il.ac.afeka.fdp.auth.data.boundary.UserBoundary;
import il.ac.afeka.fdp.auth.exceptions.InvalidCredentials;
import il.ac.afeka.fdp.auth.logger.AuthPerformance;
import il.ac.afeka.fdp.auth.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * Authentication Service
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository repository;

    /**
     * @param username username
     * @param password password
     * @return User with token details
     */
    @Override
    @Logger
    @AuthPerformance
    public UserWithToken login(String username, String password) {
        return new UserWithToken(repository.findByUsernameAndPassword(username, encryptPassword(password)).orElseThrow(InvalidCredentials::new), "");
    }

    /**
     * Logout from the system
     */
    @Override
    @Logger
    @AuthPerformance
    public void logout() {

    }

    /**
     * @param token token authorization
     * @return User details
     */
    @Override
    @Logger
    @AuthPerformance
    public UserBoundary auth(Token token) {
        // TODO implement the function
        return null;
    }

    /**
     * @param password password
     * @return encrypted password
     */
    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
