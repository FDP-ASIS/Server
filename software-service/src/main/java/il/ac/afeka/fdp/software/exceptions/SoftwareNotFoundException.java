package il.ac.afeka.fdp.software.exceptions;

public class SoftwareNotFoundException extends NotFoundException {
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
