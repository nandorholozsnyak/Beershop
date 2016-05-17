package hu.hnk.beershop.service.restrictions;

import java.util.ArrayList;
import java.util.List;

import hu.hnk.beershop.model.Rank;


/**
 * Az egyenlegfeltöltésnél való korlátozásokat megadó osztály. A korlátozásokat
 * egy Listában tároljuk, {@value MoneyTransferRestrictions#moneyRestrictions}.
 * 
 * @author Nandi
 *
 */
public class MoneyTransferRestrictions {

	/**
	 * A szabály rangja.
	 */
	private Rank rank;

	/**
	 * A korlátozott mennyiség.
	 */
	private Integer restrictedValue;

	/**
	 * A szabályokat tartalmazó statikus lista.
	 */
	private static List<MoneyTransferRestrictions> moneyRestrictions;

	static {
		moneyRestrictions = new ArrayList<>();
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.AMATUER, 3));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.SORFELELOS, 4));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.IVOBAJNOK, 4));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.SORMESTER, 5));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.SORDOKTOR, 8));
	}

	/**
	 * A szabályt létrehozó osztály konstuktora.
	 * 
	 * @param rank
	 *            a rank amihez kötjük a szabályt.
	 * @param restrictedValue
	 *            a szabály korlátozott száma.
	 */
	public MoneyTransferRestrictions(Rank rank, Integer restrictedValue) {
		super();
		this.rank = rank;
		this.restrictedValue = restrictedValue;
	}

	/**
	 * Visszaadja a rankot.
	 * 
	 * @return a rank.
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Visszaadja a korlátozott értéket.
	 * 
	 * @return a korlátozott érték.
	 */
	public Integer getRestrictedValue() {
		return restrictedValue;
	}

	/**
	 * Visszaadja a korlátozásokat tartalmazó listát.
	 * 
	 * @return a korlátozásokat tartalmazó lista.
	 */
	public static List<MoneyTransferRestrictions> getMoneyRestrictions() {
		return moneyRestrictions;
	}

}
