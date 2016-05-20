/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * A {@code DailyBuyActionLimitExceededException} kivétel a napi vásárlási limit
 * túllépése esetén dobódhat.
 * 
 * @author Nandi
 *
 */
public class DailyBuyActionLimitExceededException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyBuyActionLimitExceededException</code> kivételt.
	 */
	public DailyBuyActionLimitExceededException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyBuyActionLimitExceededException</code> kivételt a megadott
	 * üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public DailyBuyActionLimitExceededException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyBuyActionLimitExceededException</code> kivételt a megadott
	 * okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public DailyBuyActionLimitExceededException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>DailyBuyActionLimitExceededException</code> kivételt a megadott
	 * üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public DailyBuyActionLimitExceededException(String message, Throwable cause) {
		super(message, cause);
	}

}
