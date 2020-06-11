package il.ac.afeka.fdp.auth.infra;

import il.ac.afeka.fdp.auth.data.UserWithToken;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    UserWithToken login(String username, String password);
   void logout();
}
