package dao;

public interface CourseDAO {
    void addCourse(int registrationNum, String courseTitle, String description) throws Exception;
    void printCourses(int registrationNum) throws Exception;
}
