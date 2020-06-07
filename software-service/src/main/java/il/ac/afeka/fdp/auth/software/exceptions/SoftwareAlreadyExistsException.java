package il.ac.afeka.fdp.auth.software.exceptions;

public class SoftwareAlreadyExistsException extends RuntimeException {
    public SoftwareAlreadyExistsException() {
    }

    public SoftwareAlreadyExistsException(String message) {
        super(message);
    }

    public SoftwareAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public SoftwareAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
