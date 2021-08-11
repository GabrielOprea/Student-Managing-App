package dao;

import dto.Student;
import exceptions.DuplicateStudentException;
import exceptions.StudentNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStudentDAO implements StudentDAO {
    private static FileStudentDAO INSTANCE = null;
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
        } catch (EOFException e) {
        }

        file.close();
        in.close();
    }

    public static FileStudentDAO getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FileStudentDAO();
        }

        return INSTANCE;
    }

    private FileStudentDAO() {
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
}