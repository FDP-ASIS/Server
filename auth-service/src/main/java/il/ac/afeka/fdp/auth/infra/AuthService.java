package il.ac.afeka.fdp.auth.infra;

import il.ac.afeka.fdp.auth.data.Token;
import il.ac.afeka.fdp.auth.data.UserWithToken;
import il.ac.afeka.fdp.auth.data.boundary.UserBoundary;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserWithToken login(String username, String password);
    void logout();
    UserBoundary auth(Token token);
}
