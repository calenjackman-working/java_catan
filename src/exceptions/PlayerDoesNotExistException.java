package exceptions;

public class PlayerDoesNotExistException extends Exception {
    public PlayerDoesNotExistException() {
        super();
    }

    public PlayerDoesNotExistException(String msg) {
        super(msg);
    }
}
