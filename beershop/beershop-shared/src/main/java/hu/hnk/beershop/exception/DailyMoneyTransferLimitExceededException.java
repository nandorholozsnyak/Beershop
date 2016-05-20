/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * A {@code DailyMoneyTransferLimitExceededException} kivétel a napi
 * pénzfeltöltési limit túllépése esetén dobódhat.
 * 
 * @author Nandi
 *
 */
public class DailyMoneyTransferLimitExceededException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyMoneyTransferLimitExceededException</code> kivételt.
	 */
	public DailyMoneyTransferLimitExceededException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyMoneyTransferLimitExceededException</code> kivételt a megadott
	 * üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public DailyMoneyTransferLimitExceededException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyMoneyTransferLimitExceededException</code> kivételt a megadott
	 * okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public DailyMoneyTransferLimitExceededException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyMoneyTransferLimitExceededException</code> kivételt a megadott
	 * üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public DailyMoneyTransferLimitExceededException(String message, Throwable cause) {
		super(message, cause);
	}

}
