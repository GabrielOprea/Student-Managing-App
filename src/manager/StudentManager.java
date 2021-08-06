package manager;

import exceptions.DuplicateStudentException;
import exceptions.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    public static StudentManager INSTANCE = null;// private ca de asta e Singleton si e default pus pe null
    public List<Student> listStud;

    public void createStudent(String firstName, String lastName, int registrationNum) throws DuplicateStudentException {
        Student crtStud = new Student(firstName, lastName, registrationNum);
        if(listStud.contains(crtStud)) {
            throw new DuplicateStudentException();
        }
        listStud.add(crtStud);
    }

    public void setListStud(List<Student> listStud) {
        if(listStud != null) {
            this.listStud = listStud;
        }
    }

    public List<Student> getListStud() {
        return listStud;
    }

    public void showAllStudents() {
        listStud.forEach(System.out::println);
    }
    public void addCourse(int registrationNum, String courseTitle, String description) throws StudentNotFoundException {
        Student findStud = listStud.stream()
                            .filter(stud -> stud.getRegistrationNum() == registrationNum)
                            .findFirst()
                            .orElse(null);
        if(findStud == null) {
            throw new StudentNotFoundException();
        }
        findStud.addCourse(courseTitle, description);
    }

    public void printCourses(int registrationNum) throws StudentNotFoundException {
        Student findStud = listStud.stream().filter(stud -> stud.getRegistrationNum() == registrationNum)
                            .findFirst().orElse(null);
        if(findStud == null) {
            throw new StudentNotFoundException();
        }
        findStud.printCourses();
    }


    public static StudentManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StudentManager();
        }

        return INSTANCE;
    }

    private StudentManager() {
        listStud = new ArrayList<>();
    }
}
