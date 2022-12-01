package exceptions;

public class FolderNotFoundException extends Exception{

    public FolderNotFoundException() {

    }

    public FolderNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public FolderNotFoundException(Throwable cause) {
        super(cause);
    }

    public FolderNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
