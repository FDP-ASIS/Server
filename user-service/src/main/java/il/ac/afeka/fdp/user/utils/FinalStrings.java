package il.ac.afeka.fdp.user.utils;

public interface FinalStrings {

    /******************************************************************
                                CODES
     ******************************************************************/

    /**************
        200-299
     **************/
    String OK = "OK";                                               // code 200 - Ok
    String RESOURCE_DELETED = "Resource has deleted";               // code 200 - Ok
    String RESOURCE_EDITED = "Resource has edited";                 // code 200 - Ok
    String RESOURCE_CREATED = "Resource has created successfully";  // code 201 - Created
    String ALL_RESOURCES_DELETED = "All resources has deleted";     // code 204 - No Content

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
     USER MESSAGES
     **************/
    String USER_ID_MUST_BE_DIGITS = "User id must be digits only: ";
    String USER_NOT_EXISTS = "User does not exists with id: ";
    String USER_ALREADY_EXISTS = "User already exists with id: ";
    String PASSWORD_NOT_EQUAL = "password not equal to the user password in database";
}