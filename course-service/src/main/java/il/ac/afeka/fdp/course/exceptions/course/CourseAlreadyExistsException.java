package il.ac.afeka.fdp.course.exceptions.course;

import il.ac.afeka.fdp.course.exceptions.root.AlreadyExistsException;
import il.ac.afeka.fdp.course.utils.FinalStrings;

public class CourseAlreadyExistsException extends AlreadyExistsException {
    private FinalStrings finalStrings;

    public CourseAlreadyExistsException() {
        super(FinalStrings.COURSE_ONE_OR_MORE_CODES);
    }
    public CourseAlreadyExistsException(int code) {
        super(FinalStrings.COURSE_ALREADY_EXISTS + code);
    }
}
