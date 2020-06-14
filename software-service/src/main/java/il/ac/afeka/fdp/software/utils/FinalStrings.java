package il.ac.afeka.fdp.software.utils;

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
    String UNAUTHORIZED = "You are not authorized to view the resource"; // code 401
    String FORBIDDEN = "Accessing the resource you were trying to reach is forbidden"; // code 403
    String RESOURCE_NOT_FOUND = "Resource not found: "; // code 404
    String RESOURCE_EXISTS = "Failed to create the new resource because it's already exists: "; // code 409

    //500
    String SERVER_ERROR = "Internal server errors"; // code 500
}
