/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class DailyMoneyTransferLimitExceeded extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy DailyMoneyTransferLimitExceeded kivételt.
	 */
	public DailyMoneyTransferLimitExceeded() {
	}

	/**
	 * Konstuktor, mely létrehoz egy DailyMoneyTransferLimitExceeded kivételt
	 * a megadott üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public DailyMoneyTransferLimitExceeded(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy DailyMoneyTransferLimitExceeded kivételt
	 * a megadott okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public DailyMoneyTransferLimitExceeded(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy DailyMoneyTransferLimitExceeded kivételt
	 * a megadott üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public DailyMoneyTransferLimitExceeded(String message, Throwable cause) {
		super(message, cause);
	}

}
