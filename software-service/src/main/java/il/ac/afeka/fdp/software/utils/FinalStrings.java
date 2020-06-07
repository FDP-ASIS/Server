package il.ac.afeka.fdp.software.utils;

public interface FinalStrings {
    String OK = "OK"; //code 200
    String SPECIFIC_SOFTWARE_DELETED = "Specific software deleted"; // code 200
    String SPECIFIC_SOFTWARE_EDITED = "Specific software edited"; //code 200
    String SOFTWARE_CREATED = "Software created successfully!"; // code 201
    String SOFTWARE_DELETED = "All Software deleted"; // code 204
    String CREATE_BAD_REQUEST = "Could not create the software, please verify that all the required parameters are filled correctly"; // code 400
    String BAD_INPUT = "Bad request property input"; // code 400
    String UNAUTHORIZED = "Unauthorized, you can not allowed to do this"; // code 401
    String NO_SOFTWARE_FOUND = "Software with softwareName not found: "; // code 404
    String SOFTWARE_EXISTS = "Failed to create, software with this name already exists: "; // code 409
    String SERVER_ERROR = "Internal server errors"; // code 500
}
