package il.ac.afeka.fdp.course.exceptions.department;

import il.ac.afeka.fdp.course.exceptions.root.NotFoundException;
import il.ac.afeka.fdp.course.utils.FinalStrings;

public class DepartmentNotFoundException extends NotFoundException {

    public DepartmentNotFoundException() {
        super(FinalStrings.DEPARTMENT_ALREADY_EXISTS_ONE_OR_MORE_CODES);
    }

    public DepartmentNotFoundException(int code) {
        super(FinalStrings.DEPARTMENT_NOT_EXISTS + code);
    }
}
