/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * @author Nandi
 *
 */
public class UsernameAlreadyTaken extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UsernameAlreadyTaken() {
	}

	/**
	 * @param message
	 */
	public UsernameAlreadyTaken(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UsernameAlreadyTaken(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UsernameAlreadyTaken(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UsernameAlreadyTaken(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
