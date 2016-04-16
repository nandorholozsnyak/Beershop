/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * @author Nandi
 *
 */
public class NegativeCountNumber extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy NegativeCountNumber kivételt.
	 */
	public NegativeCountNumber() {
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeCountNumber kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public NegativeCountNumber(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeCountNumber kivételt a megadott okozóval.
	 * @param cause az okozó
	 */
	public NegativeCountNumber(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeCountNumber kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public NegativeCountNumber(String message, Throwable cause) {
		super(message, cause);
	}

}
