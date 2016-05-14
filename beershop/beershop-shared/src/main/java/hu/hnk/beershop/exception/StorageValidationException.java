/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * @author Nandi
 *
 */
public class StorageValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy StorageValidationException kivételt.
	 */
	public StorageValidationException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy StorageValidationException kivételt a
	 * megadott üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public StorageValidationException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy StorageValidationException kivételt a
	 * megadott okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public StorageValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy StorageValidationException kivételt a
	 * megadott üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public StorageValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
