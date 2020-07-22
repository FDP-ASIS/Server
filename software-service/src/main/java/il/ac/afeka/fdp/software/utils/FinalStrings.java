package il.ac.afeka.fdp.software.utils;

public interface FinalStrings {

    /******************************************************************
                                CODES
     ******************************************************************/

    /**************
        200-299
     **************/
    String OK = "OK";                                               // code 200 - Ok
    String COMPLETED = "Completed";                                 // code 200 - Ok
    String LOGIN= "Successfully login user";                        // code 200 - Ok
    String LOGIN_JWT= "Successfully login with jwt";                // code 200 - Ok
    String RESOURCE_DELETED = "Resource has deleted";               // code 200 - Ok
    String RESOURCE_EDITED = "Resource has edited";                 // code 200 - Ok
    String RESOURCE_CREATED = "Resource has created successfully";  // code 201 - Created
    String ALL_RESOURCES_DELETED = "All resources has deleted";     // code 204 - No Content
    String RESET_CONTENT = "Successfully logout user";              // code 205 - Reset Content

    /**************
        400-499
     **************/
    String BAD_REQUEST = "Bad request property input";                      // code 400 - Bad Request
    String INVALID_CREDENTIALS = "Invalid credentials";                     // code 401
    String UNAUTHORIZED = "You are not authorized to view the resource";    // code 401 - Unauthorized
    String FORBIDDEN = "Accessing the resource is forbidden";               // code 403 - Forbidden
    String RESOURCE_NOT_FOUND = "Resource not found: ";                     // code 404 - Not Found
    String Method_NOT_Allowed = "Method not allowed";                       // code 405 - Method Not Allowed
    String RESOURCE_EXISTS = "Failed to create the new resource because " +
            "it's already exists: ";                                        // code 409 - Conflict

    /**************
        500-599
     **************/
    String SERVER_ERROR = "Internal server error";                          // code 500 - Internal Server Error
}