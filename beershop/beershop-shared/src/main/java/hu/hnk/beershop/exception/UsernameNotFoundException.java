/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class UsernameNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt null értékkel.
	 */
	public UsernameNotFoundException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public UsernameNotFoundException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt a megadott okozóval.
	 * @param cause az okozó.
	 */
	public UsernameNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy UsernameNotFound kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public UsernameNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
