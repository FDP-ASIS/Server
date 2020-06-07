package il.ac.afeka.fdp.auth.course.exceptions;

public class BadReqException extends RuntimeException{

    public BadReqException() {
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

    public BadReqException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
