/**
 * 
 */
package hu.hnk.beershop.exception;

/**
 * A {@code StorageItemQuantityExceededException} kivétel akkor dobódhat ha egy
 * termékből a maximálisan raktárból elérhető mennyiségénél többet szeretnénk a
 * kosárba helyezni.
 * 
 * @author Nandi
 *
 */
public class StorageItemQuantityExceededException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>StorageItemQuantityExceededException</code> kivételt.
	 */
	public StorageItemQuantityExceededException() {
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>StorageItemQuantityExceededException</code> kivételt a megadott
	 * üzenettel.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 */
	public StorageItemQuantityExceededException(String message) {
		super(message);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>StorageItemQuantityExceededException</code> kivételt a megadott
	 * okozóval.
	 * 
	 * @param cause
	 *            az okozó
	 */
	public StorageItemQuantityExceededException(Throwable cause) {
		super(cause);
	}

	/**
	 * Konstuktor, mely létrehoz egy
	 * <code>StorageItemQuantityExceededException</code> kivételt a megadott
	 * üzenettel és okozóval.
	 * 
	 * @param message
	 *            a részletes üzenet.
	 * @param cause
	 *            az okozó.
	 */
	public StorageItemQuantityExceededException(String message, Throwable cause) {
		super(message, cause);
	}

}
