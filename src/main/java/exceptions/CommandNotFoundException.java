package exceptions;

public class CommandNotFoundException extends Exception{

    public CommandNotFoundException() {

    }

    public CommandNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public CommandNotFoundException(Throwable cause) {
        super(cause);
    }

    public CommandNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
