package il.ac.afeka.fdp.course.utils;

public interface FinalStrings {

    /******************************************************************
                                CODES
     ******************************************************************/

    /**************
        200-299
     **************/
    String OK = "OK";                                               // code 200 - Ok
    String RESOURCE_EDITED = "Resource has edited";                 // code 200 - Ok
    String RESOURCE_CREATED = "Resource has created successfully";  // code 201 - Created
    String RESOURCE_DELETED = "Resource has deleted";               // code 204 - No Content

    /**************
        400-499
     **************/
    String BAD_REQUEST = "Bad request property input";                      // code 400 - Bad Request
    String UNAUTHORIZED = "You are not authorized to view the resource";    // code 401 - Unauthorized
    String FORBIDDEN = "Accessing the resource is forbidden";               // code 403 - Forbidden
    String RESOURCE_NOT_FOUND = "Resource not found: ";                     // code 404 - Not Found
    String RESOURCE_EXISTS = "Failed to create the new resource because it's already exists: "; // code 409 - Conflict

    /**************
        500-599
     **************/
    String SERVER_ERROR = "Internal server error"; // code 500 - Internal Server Error

    /*******************
     EXCEPTIONS MESSAGES
     *******************/

    /**************
     COURSE MESSAGES
     **************/
    String COURSE_ONE_OR_MORE_CODES = "There are one or more courses which exists with one or more of the codes that " +
            "you have supplied";
    String COURSE_NOT_EXISTS = "Course does not exists with code: ";
    String COURSE_ALREADY_EXISTS = "Course already exists with code: ";
}