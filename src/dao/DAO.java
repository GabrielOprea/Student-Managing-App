package dao;

import exceptions.DuplicateStudentException;
import exceptions.StudentNotFoundException;

import java.io.IOException;

public interface DAO {

    void createStudent(String firstName, String lastName, int registrationNum) throws Exception;
    void showAllStudents() throws Exception;
    void addCourse(int registrationNum, String courseTitle, String description) throws Exception;
    void printCourses(int registrationNum) throws Exception;

}
