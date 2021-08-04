/*
create new student (insert: FirstName, LastName,
registration number)
show all students (prin: FirstName, LastName, number of courses)
add a course to the student (insert registration number, Course Title, Description)
show student's courses (print: Course title, Description)
All data should be kept in the file, writing and reading should happen via serialization and deserilization operations.


Acceptance Criteria

Create new students - by running this command:

registration number should be unique,  dot'n forget about validation


Show All Students - by running this command:

java -jar myaplication.jar -showAllStudents

Add a course to the student - by running this command:

java -jar myaplication.jar -addCourse -rn=7 -ct='Course Title' -cd='Course Description'

Show students's courses - by running this command:

java -jar myaplication.jar -showCourses -rn=7

-showAllStudents
-showCourses
-addCourse


 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    public static final String fileName = "students.dat";
    public static StudentManager INSTANCE = null;
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
        listStud.forEach(el-> System.out.println(el));
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

    public static void main(String[] args) throws ClassNotFoundException, StudentNotFoundException, DuplicateStudentException, IOException {

        if(args.length < 1) System.exit(1);

        String cmd = args[0];

        List<Student> students;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            students = (List<Student>) ois.readObject();
        } catch (IOException e) {
            students = null;
        }

        StudentManager.getInstance().setListStud(students);

        int registrationNumber = -1;
        String courseDescription = null, courseTitle = null, firstName = null, lastName = null;

        switch(cmd) {
            case "-createStudent":
                for(int i = 1; i < args.length; i++) {
                    if(args[i].startsWith("-rn")) {
                        registrationNumber = Integer.parseInt(args[i].substring(args[i].indexOf('=') + 1));
                    } else if(args[i].startsWith("-fn")) {
                        firstName = args[i].substring(args[i].indexOf('=') + 1);
                    } else if(args[i].startsWith("-ln")) {
                        lastName = args[i].substring(args[i].indexOf("=") + 1);
                    }
                }
                StudentManager.getInstance().createStudent(firstName, lastName, registrationNumber);
                break;
            case "-showAllStudents":
                StudentManager.getInstance().showAllStudents();
                break;
            case "-showCourses":
                if(!args[1].startsWith("-rn")) {
                    System.exit(1);
                }
                registrationNumber = Integer.parseInt(args[1].substring(args[1].indexOf('=') + 1));
                StudentManager.getInstance().printCourses(registrationNumber);
                break;
            case "-addCourse":
                for(int i = 1; i < args.length; i++) {
                    if(args[i].startsWith("-rn")) {
                        registrationNumber = Integer.parseInt(args[i].substring(args[i].indexOf('=') + 1));
                    } else if(args[i].startsWith("-ct")) {
                        courseTitle = args[i].substring(args[i].indexOf('=') + 1);
                    } else if(args[i].startsWith("-cd")) {
                        courseDescription = args[i].substring(args[i].indexOf("=") + 1);
                    }
                }
                StudentManager.getInstance().addCourse(registrationNumber, courseTitle, courseDescription);
                break;
            default:
                System.exit(1);
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(StudentManager.getInstance().getListStud());
    }
}