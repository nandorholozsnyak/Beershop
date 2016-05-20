/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * Az {@code EmailNotFoundException} kivétel egy e-mail cím sikertelen keresése
 * során dobódhat.
 * 
 * @author Nandi
 *
 */
public class EmailNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy <code>EmailNotFoundException</code>
	 * kivételt.
	 */
	public EmailNotFoundException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy <code>EmailNotFoundException</code>
	 * kivételt a megadott üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public EmailNotFoundException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy <code>EmailNotFoundException</code>
	 * kivételt a megadott okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public EmailNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy <code>EmailNotFoundException</code>
	 * kivételt a megadott üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public EmailNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
