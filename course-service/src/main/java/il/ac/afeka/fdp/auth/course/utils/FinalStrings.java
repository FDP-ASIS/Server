package il.ac.afeka.fdp.auth.course.utils;

public interface FinalStrings {
    String INDUSTRIAL_ENGINEERING_MANAGEMENT = "Industrial Engineering Management";
    String Software_Engineering = "Software Engineering";

    String OK = "OK"; //code 200
    String SPECIFIC_COURSE_DELETED = "Specific course deleted"; // code 200
    String SPECIFIC_COURSE_EDITED = "Specific course edited"; //code 200
    String COURSE_CREATED = "Course created successfully!"; // code 201
    String COURSES_DELETED = "All Courses Deleted"; // code 204
    String CREATE_BAD_REQUEST = "Could not create the course, please verify that all the required parameters are filled correctly"; // code 400
    String BAD_INPUT = "Bad request property input"; // code 400
    String UNAUTHORIZED = "Unauthorized, you can not allowed to do this"; // code 401
    String NO_COURSE_FOUND = "Course with courseCode not found: "; // code 404
    String COURSE_EXISTS = "Failed to create, course with this code number already exists: "; // code 409
    String SERVER_ERROR = "Internal server errors"; // code 500
}
