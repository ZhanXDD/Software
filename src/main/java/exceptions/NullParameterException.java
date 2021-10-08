package exceptions;

public class NullParameterException extends Exception {

	public NullParameterException() {}

	public NullParameterException(String message) {
		super(message);
	}
}
