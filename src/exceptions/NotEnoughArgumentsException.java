package exceptions;

public class NotEnoughArgumentsException extends Exception {
    @Override
    public String getMessage() {
        return "Not enough arguments!";
    }
}
