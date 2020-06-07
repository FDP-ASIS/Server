package il.ac.afeka.fdp.auth.software.exceptions;

public class SoftwareNotFoundException extends RuntimeException {
    public SoftwareNotFoundException() {
    }

    public SoftwareNotFoundException(String message) {
        super(message);
    }

    public SoftwareNotFoundException(Throwable cause) {
        super(cause);
    }

    public SoftwareNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
