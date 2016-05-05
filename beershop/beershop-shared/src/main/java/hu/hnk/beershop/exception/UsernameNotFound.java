/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class UsernameNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt null értékkel.
	 */
	public UsernameNotFound() {
	}

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public UsernameNotFound(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt a megadott okozóval.
	 * @param cause az okozó.
	 */
	public UsernameNotFound(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public UsernameNotFound(String message, Throwable cause) {
		super(message, cause);
	}

}
