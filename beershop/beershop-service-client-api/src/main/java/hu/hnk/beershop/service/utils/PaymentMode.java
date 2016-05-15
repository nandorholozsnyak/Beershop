package hu.hnk.beershop.service.utils;

/**
 * A fizetési módok enumrációja.
 * 
 * Kétféle fizetési mód lehetséges:
 * 
 * {@code PaymentMode#MONEY} - készpénzel/egyenlegre feltötött pénzel való
 * fizetés.
 * 
 * {@code PaymentMode#BONUSPOINT} - bónusz ponttal, amit a vásárlások után
 * tudunk szerezni.
 * 
 * @author Nandi
 *
 */
public enum PaymentMode {
	/**
	 * Készpénzel történő fizetés típusa.
	 */
	MONEY("money"),
	/**
	 * Bónusz ponttal történő fizetés típusa.
	 */
	BONUSPOINT("bonusPoint");

	private String value;

	private PaymentMode(String value) {
		this.value = value;
	}

	/**
	 * Visszaadja a fizetés típusának értékét.
	 * 
	 * @return a fizetés típusának értéke.
	 */
	public String getValue() {
		return value;
	}

}
