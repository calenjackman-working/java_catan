package exceptions;

public class PlayerNameTakenException extends Exception {
    public PlayerNameTakenException() {
        super();
    }

    public PlayerNameTakenException(String msg) {
        super(msg);
    }
}
