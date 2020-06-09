package il.ac.afeka.fdp.user.exception.user;

import il.ac.afeka.fdp.user.exception.root.AlreadyExistsException;
import il.ac.afeka.fdp.user.utils.FinalStrings;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
public class UserAlreadyExists extends AlreadyExistsException {
    public UserAlreadyExists(String message) {
        super(FinalStrings.USER_ALREADY_EXISTS + message);
    }
}
