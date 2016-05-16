/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class InvalidPinCodeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt.
	 */
	public InvalidPinCodeException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public InvalidPinCodeException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt a megadott okozóval.
	 * @param cause az okozó
	 */
	public InvalidPinCodeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public InvalidPinCodeException(String message, Throwable cause) {
		super(message, cause);
	}

}
