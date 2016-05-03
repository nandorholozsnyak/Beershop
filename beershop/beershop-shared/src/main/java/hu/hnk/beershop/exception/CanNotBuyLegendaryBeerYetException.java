/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * @author Nandi
 *
 */
public class CanNotBuyLegendaryBeerYetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely l�trehoz egy CanNotBuyLegendaryBeerYetException
	 * kiv�telt.
	 */
	public CanNotBuyLegendaryBeerYetException() {
	}

	/**
	 * Konstuktor, mely l�trehoz egy CanNotBuyLegendaryBeerYetException kiv�telt
	 * a megadott �zenettel.
	 * 
	 * @param message
	 *            a r�szletes �zenet.
	 */
	public CanNotBuyLegendaryBeerYetException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely l�trehoz egy CanNotBuyLegendaryBeerYetException kiv�telt
	 * a megadott okoz�val.
	 * 
	 * @param cause
	 *            az okoz�
	 */
	public CanNotBuyLegendaryBeerYetException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely l�trehoz egy CanNotBuyLegendaryBeerYetException kiv�telt
	 * a megadott �zenettel �s okoz�val.
	 * 
	 * @param message
	 *            a r�szletes �zenet.
	 * @param cause
	 *            az okoz�.
	 */
	public CanNotBuyLegendaryBeerYetException(String message, Throwable cause) {
		super(message, cause);
	}

}
