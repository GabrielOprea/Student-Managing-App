package manager;

import dao.interfaces.CourseDAO;
import dao.DAOFactory;

import dao.interfaces.StudentDAO;
import dto.Course;
import exceptions.IncompleteArgumentsException;
import exceptions.NotEnoughArgumentsException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
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
        int registrationNumber = -1;
        String courseDescription = null, courseTitle = null, firstName = null, lastName = null;

        String inputType = "hibernate";
        StudentDAO studentDao = new DAOFactory().getStudentDAO(inputType);
        CourseDAO courseDao = new DAOFactory().getCourseDAO(inputType);

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
                studentDao.createStudent(firstName, lastName, registrationNumber);
                break;

            case "-createStudentWithCourses":
                for(int i = 1; i <= 3; i++) {
                    if (args[i].startsWith("-rn")) {
                        registrationNumber = Integer.parseInt(args[i].substring(args[i].indexOf('=') + 1));
                    } else if (args[i].startsWith("-fn")) {
                        firstName = args[i].substring(args[i].indexOf('=') + 1);
                    } else if (args[i].startsWith("-ln")) {
                        lastName = args[i].substring(args[i].indexOf("=") + 1);
                    }
                }
                if (lastName == null || firstName == null || registrationNumber == -1) {
                    throw new IncompleteArgumentsException();
                }

                List<Course> courses = new ArrayList<>();
                for(int i = 4; i < args.length; i += 2) {
                    if(args[i].startsWith("-ct")) {
                        courseTitle = args[i].substring(args[i].indexOf('=') + 1);
                    } else courseDescription = args[i].substring(args[i].indexOf("=") + 1);

                    if (args[i + 1].startsWith("-ct")) {
                        courseTitle = args[i + 1].substring(args[i + 1].indexOf('=') + 1);
                    } else courseDescription = args[i + 1].substring(args[i + 1].indexOf("=") + 1);

                    Course course = new Course(courseTitle, courseDescription, registrationNumber);
                    courses.add(course);
                }
                studentDao.createStudentWithCourses(firstName, lastName, registrationNumber, courses);
                break;
            case "-showAllStudents":

                studentDao.showAllStudents();
                break;
            case "-showCourses":
                if(!args[1].startsWith("-rn")) {
                    throw new IncompleteArgumentsException();
                }
                registrationNumber = Integer.parseInt(args[1].substring(args[1].indexOf('=') + 1));
                courseDao.printCourses(registrationNumber);
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
                courseDao.addCourse(registrationNumber, courseTitle, courseDescription);
                break;
            default:
                System.exit(1);
        }

    }
}