package exceptions;

public class IncompleteArgumentsException extends Exception {
    @Override
    public String getMessage() {
        return "Incomplete arguments!";
    }
}
