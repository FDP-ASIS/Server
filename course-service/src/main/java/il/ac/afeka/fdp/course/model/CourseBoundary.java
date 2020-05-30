package il.ac.afeka.fdp.course.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class CourseBoundary {
    private String id;
    @NonNull
    private String courseName;
    @NonNull
    private String courseCode;
    @NonNull
    private Department department;
    @NonNull
    private String softwareList;

    public CourseBoundary() {
        super();
    }

    public CourseBoundary(String courseName, String courseCode, Department department,String softwareList) {
        super();
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.department= department;
        this.softwareList= softwareList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getSoftwareList() { return softwareList; }

    public void setSoftwareList(String softwareList) {
        this.softwareList = softwareList;
    }
}
