/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class CanNotBuyLegendaryBeerYetExceptionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy CanNotBuyLegendaryBeerYetException
	 * kivételt.
	 */
	public CanNotBuyLegendaryBeerYetExceptionException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy CanNotBuyLegendaryBeerYetException kivételt
	 * a megadott üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public CanNotBuyLegendaryBeerYetExceptionException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy CanNotBuyLegendaryBeerYetException kivételt
	 * a megadott okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public CanNotBuyLegendaryBeerYetExceptionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy CanNotBuyLegendaryBeerYetException kivételt
	 * a megadott üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public CanNotBuyLegendaryBeerYetExceptionException(String message, Throwable cause) {
		super(message, cause);
	}

}
