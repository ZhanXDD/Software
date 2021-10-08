package exceptions;

public class IncorrectPaymentFormatException extends Exception {

	public IncorrectPaymentFormatException() {}

	public IncorrectPaymentFormatException(String message) {
		super(message);
	}
}
