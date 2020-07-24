package il.ac.afeka.fdp.auth.utils;

public interface FinalStrings {

    /******************************************************************
                                CODES
     ******************************************************************/

    /**************
        200-299
     **************/
    String LOGIN= "Successfully login user";                        // code 200 - Ok
    String LOGIN_JWT= "Successfully login with jwt";                // code 200 - Ok
    String RESET_CONTENT = "Successfully logout user";              // code 205 - Reset Content

    /**************
        400-499
     **************/
    String INVALID_CREDENTIALS = "Invalid credentials";                     // code 401
    String UNAUTHORIZED = "You are not authorized to view the resource";    // code 401 - Unauthorized
    String FORBIDDEN = "Accessing the resource is forbidden";               // code 403 - Forbidden

    /**************
        500-599
     **************/
    String SERVER_ERROR = "Internal server error";                          // code 500 - Internal Server Error
}