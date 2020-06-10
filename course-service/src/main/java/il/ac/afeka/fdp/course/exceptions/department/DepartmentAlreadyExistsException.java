package il.ac.afeka.fdp.course.exceptions.department;

import il.ac.afeka.fdp.course.exceptions.root.AlreadyExistsException;
import il.ac.afeka.fdp.course.exceptions.root.NotFoundException;
import il.ac.afeka.fdp.course.utils.FinalStrings;

public class DepartmentAlreadyExistsException extends AlreadyExistsException {
    private FinalStrings finalStrings;

    public DepartmentAlreadyExistsException() {
        super(FinalStrings.DEPARTMENT_ALREADY_EXISTS_ONE_OR_MORE_CODES);
    }
    public DepartmentAlreadyExistsException(int code) {
        super(FinalStrings.DEPARTMENT_ALREADY_EXISTS + code);
    }
}
