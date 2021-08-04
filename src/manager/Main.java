package manager;

import exceptions.IncompleteArgumentsException;
import exceptions.NotEnoughArgumentsException;

import java.io.*;
import java.util.List;

public class Main {

    public static final String filename = "students.dat";

    private static void parseInput(String[] args) throws Exception {
        if(args.length < 1) {
            throw new NotEnoughArgumentsException();
        }

        String cmd = args[0];

        List<Student> students;
        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
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
                if(lastName == null || firstName == null || registrationNumber == -1) {
                    throw new IncompleteArgumentsException();
                }
                StudentManager.getInstance().createStudent(firstName, lastName, registrationNumber);
                break;
            case "-showAllStudents":

                StudentManager.getInstance().showAllStudents();
                break;
            case "-showCourses":
                if(!args[1].startsWith("-rn")) {
                    throw new IncompleteArgumentsException();
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
                if(registrationNumber == -1 || courseTitle == null || courseDescription == null) {
                    throw new IncompleteArgumentsException();
                }
                StudentManager.getInstance().addCourse(registrationNumber, courseTitle, courseDescription);
                break;
            default:
                System.exit(1);
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(StudentManager.getInstance().getListStud());
    }

    public static void main(String[] args) {
        try {
            parseInput(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}