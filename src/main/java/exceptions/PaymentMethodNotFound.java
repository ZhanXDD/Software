package exceptions;

public class PaymentMethodNotFound extends Exception {

	public PaymentMethodNotFound() {}

	public PaymentMethodNotFound(String message) {
		super(message);
	}
}
