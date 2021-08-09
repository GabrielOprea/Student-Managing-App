package manager;

import dao.DAO;
import dao.DAOFactory;
import dao.DatabaseDAO;
import dao.FileDAO;
import exceptions.IncompleteArgumentsException;
import exceptions.NotEnoughArgumentsException;

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

        DAO dao = new DAOFactory().getDAO("file");

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
                dao.createStudent(firstName, lastName, registrationNumber);
                break;
            case "-showAllStudents":

                dao.showAllStudents();
                break;
            case "-showCourses":
                if(!args[1].startsWith("-rn")) {
                    throw new IncompleteArgumentsException();
                }
                registrationNumber = Integer.parseInt(args[1].substring(args[1].indexOf('=') + 1));
                dao.printCourses(registrationNumber);
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
                dao.addCourse(registrationNumber, courseTitle, courseDescription);
                break;
            default:
                System.exit(1);
        }

    }
}