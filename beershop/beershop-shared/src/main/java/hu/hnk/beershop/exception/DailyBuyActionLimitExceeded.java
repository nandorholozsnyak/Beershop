/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class DailyBuyActionLimitExceeded extends RestrictionValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy DailyBuyActionLimitExceeded kivételt.
	 */
	public DailyBuyActionLimitExceeded() {
	}

	/**
	 * Konstuktor, mely létrehoz egy DailyBuyActionLimitExceeded kivételt a
	 * megadott üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public DailyBuyActionLimitExceeded(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy DailyBuyActionLimitExceeded kivételt a
	 * megadott okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public DailyBuyActionLimitExceeded(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy DailyBuyActionLimitExceeded kivételt a
	 * megadott üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public DailyBuyActionLimitExceeded(String message, Throwable cause) {
		super(message, cause);
	}

}
