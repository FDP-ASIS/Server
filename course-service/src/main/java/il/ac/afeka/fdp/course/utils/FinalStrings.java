package il.ac.afeka.fdp.course.utils;

public interface FinalStrings {

    //CODES

    //100

    //200
    String OK = "OK"; //code 200
    String RESOURCE_DELETED = "Resource has deleted"; // code 200
    String RESOURCE_EDITED = "Resource has edited"; //code 200
    String RESOURCE_CREATED = "Resource has created successfully"; //code 201
    String ALL_RESOURCES_DELETED = "All resource has deleted"; //code 204

    //300

    //400
    String BAD_REQUEST = "Bad request property input"; // code 400
    String UNAUTHORIZED = "Unauthorized, you can not allowed to do this"; // code 401
    String FORBIDDEN = "Accessing the resource you were trying to reach is forbidden"; // code 403
    String RESOURCE_NOT_FOUND = "Resource not found: "; // code 404
    String RESOURCE_EXISTS = "Failed to create the new resource because it's already exists: "; // code 409

    //500
    String SERVER_ERROR = "Internal server errors"; // code 500

    //EXCEPTIONS MESSAGES
    String EMPTY_FILED = "There are one or more empty fields";

    //COURSE
    String COURSE_ONE_OR_MORE_CODES = "There are one or more course which exists with one or more of the codes that you have supplied";
    String COURSE_NOT_EXISTS = "Course doe's exists with code ";
    String COURSE_ALREADY_EXISTS = "Course already exists with code ";

    //DEPARTMENT
    String DEPARTMENT_ALREADY_EXISTS_ONE_OR_MORE_CODES = "There are one or more departments which exists with one or more of the codes that you have supplied";
    String DEPARTMENT_NOT_EXISTS = "Department doe's exists with code ";
    String DEPARTMENT_ALREADY_EXISTS = "Department already exists with code ";
}