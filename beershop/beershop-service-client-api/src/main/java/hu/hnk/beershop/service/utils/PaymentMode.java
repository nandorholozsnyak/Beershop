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

	/**
	 * A vásárlási mód leírása, értéke.
	 */
	private String value;

	/**
	 * A fizetési mód enumeráció privát konstuktora.
	 * 
	 * @param value
	 *            a fizetési mód leírása, értéke.
	 */
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
