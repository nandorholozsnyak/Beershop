/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class EmailNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy EmailNotFound kivételt.
	 */
	public EmailNotFound() {
	}

	/**
	 * Konstuktor, mely létrehoz egy EmailNotFound kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public EmailNotFound(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy EmailNotFound kivételt a megadott okozóval.
	 * @param cause az okozó
	 */
	public EmailNotFound(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy EmailNotFound kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public EmailNotFound(String message, Throwable cause) {
		super(message, cause);
	}

}
