package dao.interfaces;

import dto.Course;

import java.util.List;

public interface StudentDAO {

    void createStudent(String firstName, String lastName, int registrationNum) throws Exception;
    void showAllStudents() throws Exception;
    void createStudentWithCourses(String firstName, String lastName, int registrationNum,
                                  List<Course> courses);
}
