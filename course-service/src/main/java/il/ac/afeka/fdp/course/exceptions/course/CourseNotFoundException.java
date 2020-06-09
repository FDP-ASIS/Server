package il.ac.afeka.fdp.course.exceptions.course;

import il.ac.afeka.fdp.course.exceptions.root.NotFoundException;
import il.ac.afeka.fdp.course.utils.FinalStrings;

public class CourseNotFoundException extends NotFoundException {
    private FinalStrings finalStrings;

    public CourseNotFoundException(long code) {
        super(FinalStrings.COURSE_NOT_EXISTS + code);
    }
}
