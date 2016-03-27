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
	Amatuer(10),

	/**
	 * Kezdő.
	 */
	Beginner(20),

	/**
	 * Profi.
	 */
	Expert(30);

	Rank(final Integer value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private int value;

}
