package il.ac.afeka.fdp.user.exception.user;

import il.ac.afeka.fdp.user.exception.root.NotFoundException;
import il.ac.afeka.fdp.user.utils.FinalStrings;

public class UserNotFound extends NotFoundException {
    public UserNotFound(String message) {
        super(FinalStrings.USER_NOT_EXISTS + message);
    }
}
