package hu.hnk.beershop.service.discounts;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.hnk.beershop.model.Rank;
import lombok.Builder;

/**
 * A különböző rangokhoz tartozó kedvezményeket leíró osztály.
 * 
 * Minden egyes rang bizonyos kiváltságokat élvehez a vásárlás során.
 * 
 * @author Nandi
 *
 */
@Builder
public class DailyRankBonus {

	private List<Rank> ranks;
	private DayOfWeek day;
	private List<DiscountType> discounts;

	private static final List<DailyRankBonus> DAILY_BONUSES;

	static {
		DAILY_BONUSES = new ArrayList<>();

		// Amatőrök keddenként kapnak kedvezményeket.
		// Feláras lesz minden.
		getDailyBonuses().add(DailyRankBonus.builder()
				.day(DayOfWeek.TUESDAY)
				.ranks(Arrays.asList(Rank.AMATUER))
				.discounts(Arrays.asList(DiscountType.FIFTYPERCENTAGE))
				.build());

		// Sörfelelősök illetve Sörmesterek extra bónusz pontokat kapnak
		// szerdánként.
		getDailyBonuses().add(DailyRankBonus.builder()
				.day(DayOfWeek.WEDNESDAY)
				.ranks(Arrays.asList(Rank.SORFELELOS, Rank.SORMESTER))
				.discounts(Arrays.asList(DiscountType.EXTRABONUSPOINTS))
				.build());

		// Csütörtökönként az ivóbajnok a legolcsóbb termékeket ingyen kapja
		// meg.
		getDailyBonuses().add(DailyRankBonus.builder()
				.day(DayOfWeek.THURSDAY)
				.ranks(Arrays.asList(Rank.IVOBAJNOK))
				.discounts(Arrays.asList(DiscountType.THECHEAPESTFORFREE))
				.build());

		// Szombatonként mindenkinek ingyenes szállítás van.
		getDailyBonuses().add(DailyRankBonus.builder()
				.day(DayOfWeek.SATURDAY)
				.ranks(Arrays.asList(Rank.values()))
				.discounts(Arrays.asList(DiscountType.FREESHIPPING))
				.build());
	}

	/**
	 * Konstuktor, mely létrehoz egy napi bónusz objektumot.
	 * 
	 * @param ranks
	 *            a jogosult rangok listája.
	 * @param day
	 *            a kedvezmény napja.
	 * @param discounts
	 *            a kedvezmények listája.
	 */
	public DailyRankBonus(List<Rank> ranks, DayOfWeek day, List<DiscountType> discounts) {
		super();
		this.ranks = ranks;
		this.day = day;
		this.discounts = discounts;
	}

	/**
	 * Visszaadja a kedvezmény napját.
	 * 
	 * @return a kedvezmény napja.
	 */
	public DayOfWeek getDay() {
		return day;
	}

	/**
	 * Visszaadja a kedvezmények listáját.
	 * 
	 * @return kedvezmények listája.
	 */
	public List<DiscountType> getDiscounts() {
		return discounts;
	}

	/**
	 * Visszaadja a kedvezményre jogosult rangokat.
	 * 
	 * @return a jogosult rangok listája.
	 */
	public List<Rank> getRanks() {
		return ranks;
	}

	/**
	 * Visszaadja a már előre definiált rang - kedvemények - nap kapcsolatokat
	 * tartalmazó listát.
	 * 
	 * @return az előre definiált napi kedvezmények listája.
	 */
	public static List<DailyRankBonus> getDailyBonuses() {
		return DAILY_BONUSES;
	}

}
