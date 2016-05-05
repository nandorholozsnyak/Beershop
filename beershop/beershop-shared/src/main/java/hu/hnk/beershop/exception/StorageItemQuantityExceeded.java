/**
 * 
 */
package hu.hnk.beershop.exception;


/**
 * @author Nandi
 *
 */
public class StorageItemQuantityExceeded extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy StorageQuantityExceeded kivételt.
	 */
	public StorageItemQuantityExceeded() {
	}

	/**
	 * Konstuktor, mely létrehoz egy StorageQuantityExceeded kivételt a megadott üzenettel.
	 * @param message a részletes üzenet.
	 */
	public StorageItemQuantityExceeded(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy StorageQuantityExceeded kivételt a megadott okozóval.
	 * @param cause az okozó
	 */
	public StorageItemQuantityExceeded(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy StorageQuantityExceeded kivételt a megadott üzenettel és okozóval.
	 * @param message a részletes üzenet.
	 * @param cause az okozó.
	 */
	public StorageItemQuantityExceeded(String message, Throwable cause) {
		super(message, cause);
	}

}
