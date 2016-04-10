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
	 * 
	 */
	public EmailNotFound() {
	}

	/**
	 * @param message
	 */
	public EmailNotFound(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public EmailNotFound(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmailNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
