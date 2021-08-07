package manager;

import exceptions.DuplicateStudentException;
import exceptions.StudentNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private static StudentManager INSTANCE = null;
    private List<Student> listStud;

    private final String file = "students1.dat";

    public void createStudent(String firstName, String lastName, int registrationNum) throws DuplicateStudentException, IOException {
        Student crtStud = new Student(firstName, lastName, registrationNum);
        if(listStud.contains(crtStud)) {
            throw new DuplicateStudentException();
        }
        FileOutputStream file = new FileOutputStream(this.file);
        ObjectOutputStream out = new ObjectOutputStream(file);

        listStud.add(crtStud);
        for (Student stud : listStud) {
            out.writeObject(stud);
        }

        file.close();
        out.close();
    }

    public void setListStud(List<Student> listStud) {
        if(listStud != null) {
            this.listStud = listStud;
        }
    }

    public void showAllStudents() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(this.file);
        ObjectInputStream in = new ObjectInputStream(file);

        try {
            while (true) {
                Object oa = in.readObject();
                Student stud = (Student) oa;
                System.out.println(stud);
            }
        }
        catch (EOFException e) {
        }

        file.close();
        in.close();
    }

    public void addCourse(int registrationNum, String courseTitle, String description) throws StudentNotFoundException, IOException {
        Student findStud = listStud.stream()
                            .filter(stud -> stud.getRegistrationNum() == registrationNum)
                            .findFirst()
                            .orElse(null);
        if(findStud == null) {
            throw new StudentNotFoundException();
        }
        findStud.addCourse(courseTitle, description);

        FileOutputStream file = new FileOutputStream(this.file);
        ObjectOutputStream out = new ObjectOutputStream(file);

        for(Student stud: listStud) {
            out.writeObject(stud);
        }

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