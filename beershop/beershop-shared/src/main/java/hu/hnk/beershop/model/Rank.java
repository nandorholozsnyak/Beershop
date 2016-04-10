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
	 * Kezdõ.
	 */
	Beginner(20L),

	/**
	 * Profi.
	 */
	Expert(30L);

	private Long value;

	Rank(Long value) {
		this.value = value;
	}

	public static Rank getValue(Long value) {
		for (Rank item : Rank.values()) {
			if(item.value == value) {
				return item;
			}
		}
		return null;
	}

}
