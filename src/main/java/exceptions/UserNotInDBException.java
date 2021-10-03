package exceptions;

public class UserNotInDBException extends Exception {

	public UserNotInDBException() {
	}

	public UserNotInDBException(String message) {
		super(message);
	}
}
