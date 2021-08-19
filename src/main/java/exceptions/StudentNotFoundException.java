package exceptions;

public class StudentNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Student with requested ID not found!";
    }
}
