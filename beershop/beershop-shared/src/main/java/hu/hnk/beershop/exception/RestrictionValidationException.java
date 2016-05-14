/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * @author Nandi
 *
 */
public class RestrictionValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy RestrictionValidationException kivételt.
	 */
	public RestrictionValidationException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy RestrictionValidationException kivételt a
	 * megadott üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public RestrictionValidationException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy RestrictionValidationException kivételt a
	 * megadott okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public RestrictionValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy RestrictionValidationException kivételt a
	 * megadott üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public RestrictionValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
