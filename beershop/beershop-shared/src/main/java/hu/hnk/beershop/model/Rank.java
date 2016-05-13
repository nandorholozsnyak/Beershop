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
	Amatuer("Amatőr"),

	/**
	 * Sörfelelős.
	 */
	Sorfelelos("Sörfelelős"),

	/**
	 * Sörmester.
	 */
	Sormester("Sörmester"),

	/**
	 * Ivobajnok.
	 */
	Ivobajnok("Ivóbajnok"),

	/**
	 * Sördoktor.
	 */
	Sordoktor("Sördoktor"),

	/**
	 * Legenda.
	 */
	Legenda("Legenda");

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
	 * @return
	 */
	public String getRankName() {
		return rankName;
	}

}
