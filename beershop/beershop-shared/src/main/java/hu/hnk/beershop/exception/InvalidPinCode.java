/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class InvalidPinCode extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt.
	 */
	public InvalidPinCode() {
	}

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public InvalidPinCode(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt a megadott okozóval.
	 * @param cause az okozó
	 */
	public InvalidPinCode(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy InvalidPinCode kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public InvalidPinCode(String message, Throwable cause) {
		super(message, cause);
	}

}
