package il.ac.afeka.fdp.auth.course.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {

    public CourseAlreadyExistsException() {
    }

    public CourseAlreadyExistsException(String message) {
        super(message);
    }

    public CourseAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CourseAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
