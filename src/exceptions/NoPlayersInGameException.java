package exceptions;

public class NoPlayersInGameException extends Exception {
    public NoPlayersInGameException() {
        super();
    }

    public NoPlayersInGameException(String msg) {
        super(msg);
    }
}
