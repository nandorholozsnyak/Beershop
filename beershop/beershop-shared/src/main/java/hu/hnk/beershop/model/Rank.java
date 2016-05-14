package hu.hnk.beershop.model;

/**
 * A rank enumeráció amely az egyes felhasználók rangjai lehetnek.
 * 
 * @author Nandi
 *
 */
public enum Rank {
	/**
	 * Amatőr.
	 */
	AMATUER("Amatőr"),

	/**
	 * Sörfelelős.
	 */
	SORFELELOS("Sörfelelős"),

	/**
	 * Sörmester.
	 */
	SORMESTER("Sörmester"),

	/**
	 * Ivobajnok.
	 */
	IVOBAJNOK("Ivóbajnok"),

	/**
	 * Sördoktor.
	 */
	SORDOKTOR("Sördoktor"),

	/**
	 * Legenda.
	 */
	LEGENDA("Legenda");

	/**
	 * A rang teljes magyar megfeleltetése.
	 */
	private String rankName;

	/**
	 * Konstuktor, mely létrehoz egy Rang objektumot a megadott értékkel.
	 * 
	 * @param rankName
	 *            a rang neve
	 */
	Rank(String rankName) {
		this.rankName = rankName;
	}

	/**
	 * Megadja egy választott érték rangját.
	 * 
	 * @param rankName
	 *            a rang neve
	 * @return a hozzá tartozó Rank
	 */
	public static Rank getValue(String rankName) {
		for (Rank item : Rank.values()) {
			if (item.rankName.equals(rankName)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Visszaadja a rang nevét.
	 * 
	 * @return a rang neve
	 */
	public String getRankName() {
		return rankName;
	}

}
