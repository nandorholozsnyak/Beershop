/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * @author Nandi
 *
 */
public class NegativeQuantityNumber extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt.
	 */
	public NegativeQuantityNumber() {
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public NegativeQuantityNumber(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt a megadott okozóval.
	 * @param cause az okozó
	 */
	public NegativeQuantityNumber(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy NegativeQuantityNumber kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public NegativeQuantityNumber(String message, Throwable cause) {
		super(message, cause);
	}

}
