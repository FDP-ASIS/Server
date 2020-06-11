package il.ac.afeka.fdp.user.exception.user;

import il.ac.afeka.fdp.user.exception.root.BadReqException;
import il.ac.afeka.fdp.user.utils.FinalStrings;

public class IdMustBeOnlyDigits extends BadReqException {
    public IdMustBeOnlyDigits(String value) {
        super(FinalStrings.USER_ID_MUST_BE_DIGITS + value);
    }
}
