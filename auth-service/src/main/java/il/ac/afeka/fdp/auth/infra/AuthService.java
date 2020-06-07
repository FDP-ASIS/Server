package il.ac.afeka.fdp.auth.infra;

import il.ac.afeka.fdp.auth.data.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    UserEntity login(String username, String password);

   void logout();

}
