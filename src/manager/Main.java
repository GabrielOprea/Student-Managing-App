package manager;

import java.sql.*;
import exceptions.IncompleteArgumentsException;
import exceptions.NotEnoughArgumentsException;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentmanaging", "root",
                    "aDesea132_");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from students");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        try {
            parseInput(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseInput(String[] args) throws Exception {
        if(args.length < 1) {
            throw new NotEnoughArgumentsException();
        }

        String cmd = args[0];

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

            while (true) {
                students.add((Student) oa);
                oa = ois.readObject();
            }
        } catch(EOFException e) {
            ois.close();
        } catch(IOException e) {
            students = new ArrayList<Student>();
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

    }
}