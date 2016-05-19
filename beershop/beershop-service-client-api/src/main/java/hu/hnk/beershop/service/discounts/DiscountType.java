package hu.hnk.beershop.service.discounts;

/**
 * A kedvezmények típusát tartalmazó enumeráció.
 * 
 * Többféle kedvezményt is tudunk definiálni, ebben az enumerációban tüntetjük
 * fel ezeket, emellett kapnak egy rövidebb leírást is amit képesek leszünk majd
 * a UI felületen megjeleníteni ha szükséges.
 * 
 * A kedvezmények típusai:
 * <ul>
 * <li>{@link DiscountType#FIFTYPERCENTAGE} - 50% kedvezmény a vásárlás során.
 * </li>
 * <li>{@link DiscountType#EXTRABONUSPOINTS} - extra bónusz pont.</li>
 * <li>{@link DiscountType#FREESHIPPING} - ingyenes szállítás.</li>
 * <li>{@link DiscountType#EXTRAMONEYTRANSFER} - extra/bónusz egyenlegfeltöltési
 * lehetőség.</li>
 * 
 * <li>{@link DiscountType#THECHEAPESTFORFREE} - a legolcsóbb ingyen.</li>
 * </ul>
 * 
 * @author Nandi
 *
 */
public enum DiscountType {

	/**
	 * 50% kedvezményt jelző érték típus.
	 */
	FIFTYPERCENTAGE("50% kedvezmény"),
	/**
	 * Extra bónusz pontot jelző érték típus.
	 */
	EXTRABONUSPOINTS("Extra bónusz pontok"),
	/**
	 * Ingyenes szállítást jelző érték típus.
	 */
	FREESHIPPING("Ingyenes szállítás"),
	/**
	 * Extra/bónusz feltöltési lehetőséget jelző érték típus.
	 */
	EXTRAMONEYTRANSFER("Extra lehetőség az egyenleg feltöltésére"),
	/**
	 * "A legolcsóbb ingyen" kedvezményt jelző érték típus.
	 */
	THECHEAPESTFORFREE("A legolcsóbb ingyen");

	/**
	 * A kedvezmény rövid leírása.
	 */
	private String value;

	/**
	 * A kedvezmény típus privát konstuktora.
	 * 
	 * @param value
	 *            a kedvezmény rövid leírása, értéke.
	 */
	private DiscountType(String value) {
		this.value = value;
	}

	/**
	 * Visszaadja a kedvezmény rövid leírását.
	 * 
	 * @return a kedvezmény rövid leírása.
	 */
	public String getValue() {
		return value;
	}

}
