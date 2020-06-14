package il.ac.afeka.fdp.software.exceptions;

import il.ac.afeka.fdp.software.utils.FinalStrings;

public class BadReqException extends RuntimeException{

    public BadReqException() {
        this(FinalStrings.BAD_REQUEST);
    }

    public BadReqException(String message) {
        super(message);
    }

    public BadReqException(Throwable cause) {
        super(cause);
    }

    public BadReqException(String message, Throwable cause) {
        super(message, cause);
    }

}
