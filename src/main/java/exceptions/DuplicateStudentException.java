package exceptions;

public class DuplicateStudentException extends Exception {
    @Override
    public String getMessage() {
        return "Duplicate Student!";
    }
}
