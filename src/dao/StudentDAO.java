package dao;

import exceptions.DuplicateStudentException;
import exceptions.StudentNotFoundException;

import java.io.IOException;

public interface StudentDAO {

    void createStudent(String firstName, String lastName, int registrationNum) throws Exception;
    void showAllStudents() throws Exception;
}
