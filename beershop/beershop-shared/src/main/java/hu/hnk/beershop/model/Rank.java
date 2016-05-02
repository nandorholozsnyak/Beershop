package hu.hnk.beershop.model;

/**
 * A rank enumeráció amely az egyes felhasználók rangjai lehetnek.
 * 
 * @author Nandi
 *
 */
public enum Rank {
	/**
	 * Amatõr.
	 */
	Amatuer(10L),

	/**
	 * Sörfelelõs.
	 */
	Sorfelelos(20L),

	/**
	 * Sörmester.
	 */
	Sormester(30L),

	/**
	 * Ivobajnok.
	 */
	Ivobajnok(40L),

	/**
	 * Sördoktor.
	 */
	Sordoktor(50L),

	/**
	 * Legenda.
	 */
	Legenda(60L);

	/**
	 * A rank értéke.
	 */
	private Long value;

	/**
	 * Konstuktor, mely létrehoz egy Rang objektumot a megadott értékkel.
	 * 
	 * @param value
	 *            a rank értéke
	 */
	Rank(Long value) {
		this.value = value;
	}

	/**
	 * Megadja egy választott érték rangját.
	 * 
	 * @param value
	 *            a megadott érték
	 * @return a hozzá tartozó Rank
	 */
	public static Rank getValue(Long value) {
		for (Rank item : Rank.values()) {
			if (item.value == value) {
				return item;
			}
		}
		return null;
	}

}
