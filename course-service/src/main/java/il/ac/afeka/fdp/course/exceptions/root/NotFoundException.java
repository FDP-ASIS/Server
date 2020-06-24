package il.ac.afeka.fdp.course.exceptions.root;

import il.ac.afeka.fdp.course.utils.FinalStrings;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this(FinalStrings.RESOURCE_NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}