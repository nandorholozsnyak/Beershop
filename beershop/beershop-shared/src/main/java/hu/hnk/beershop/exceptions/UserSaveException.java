/**
 * 
 */
package hu.hnk.beershop.exceptions;

/**
 * @author Nandi
 *
 */
public class UserSaveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6169121311929053758L;

	public UserSaveException() {
		super();
	}

	public UserSaveException(Exception e) {
		super(e);
	}

	public UserSaveException(Exception e, String message) {
		super(message, e);
	}

}
