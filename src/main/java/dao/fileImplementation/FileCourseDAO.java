package dao.fileImplementation;

import dao.interfaces.CourseDAO;
import dto.Student;
import exceptions.StudentNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCourseDAO implements CourseDAO {

    private static FileCourseDAO INSTANCE = null;
    private List<Student> listStud;

    private final String file = "students1.dat";
    public void addCourse(int registrationNum, String courseTitle, String description) throws StudentNotFoundException, IOException {
        Student findStud = listStud.stream()
                .filter(stud -> stud.getRegistrationNumber() == registrationNum)
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
        Student findStud = listStud.stream().filter(stud -> stud.getRegistrationNumber() == registrationNum)
                .findFirst().orElse(null);
        if(findStud == null) {
            throw new StudentNotFoundException();
        }
        findStud.printCourses();
    }

    public void setListStud(List<Student> listStud) {
        if(listStud != null) {
            this.listStud = listStud;
        }
    }

    private FileCourseDAO() {
        listStud = new ArrayList<>();
        final String filename = "students1.dat";

        List<Student> students = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            Object oa = ois.readObject();
            if (oa == null) {
                students = null;
            } else {
                students = new ArrayList<Student>();
            }

            try {
                while (true) {
                    students.add((Student) oa);
                    oa = ois.readObject();
                }
            } catch(EOFException e) {
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setListStud(students);
    }

    public static FileCourseDAO getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FileCourseDAO();
        }

        return INSTANCE;
    }
}
