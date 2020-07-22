package il.ac.afeka.fdp.user.exception.user;

import il.ac.afeka.fdp.user.exception.root.BadReqException;
import il.ac.afeka.fdp.user.utils.FinalStrings;

public class PasswordNotEqual extends BadReqException {
    public PasswordNotEqual() {
        super(FinalStrings.PASSWORD_NOT_EQUAL);
    }
}
