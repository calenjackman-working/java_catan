package exceptions;

public class TooManyPlayersException extends Exception {
	public TooManyPlayersException() {
		super();
	}

	public TooManyPlayersException(String msg) {
		super(msg);
	}
}
