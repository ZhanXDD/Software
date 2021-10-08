package exceptions;

public class NoPaymentMethodException extends Exception {

	public NoPaymentMethodException() {}

	public NoPaymentMethodException(String message) {
		super(message);
	}
}
