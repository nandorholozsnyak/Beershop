/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * A {@code CanNotBuyLegendaryBeerYetException} kivétel a legendás sörök
 * vásárlásakor dobódhat.
 * 
 * @author Nandi
 *
 */
public class CanNotBuyLegendaryBeerYetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>CanNotBuyLegendaryBeerYetException</code> kivételt.
	 */
	public CanNotBuyLegendaryBeerYetException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>CanNotBuyLegendaryBeerYetException</code> kivételt a megadott
	 * üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public CanNotBuyLegendaryBeerYetException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>CanNotBuyLegendaryBeerYetException</code> kivételt a megadott
	 * okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public CanNotBuyLegendaryBeerYetException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>CanNotBuyLegendaryBeerYetException</code> kivételt a megadott
	 * üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public CanNotBuyLegendaryBeerYetException(String message, Throwable cause) {
		super(message, cause);
	}

}
