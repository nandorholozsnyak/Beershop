/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class NegativeQuantityNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt.
	 */
	public NegativeQuantityNumberException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public NegativeQuantityNumberException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt a megadott okozóval.
	 * @param cause az okozó
	 */
	public NegativeQuantityNumberException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public NegativeQuantityNumberException(String message, Throwable cause) {
		super(message, cause);
	}

}
