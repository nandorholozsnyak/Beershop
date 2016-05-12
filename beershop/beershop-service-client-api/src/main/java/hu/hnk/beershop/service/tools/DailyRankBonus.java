package hu.hnk.beershop.service.tools;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.service.tools.DiscountType;
import lombok.Builder;

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
				.ranks(Arrays.asList(Rank.Amatuer))
				.discounts(Arrays.asList(DiscountType.FiftyPercentage))
				.build());

		// Sörfelelősök illetve Sörmesterek extra bónusz pontokat kapnak
		// szerdánként.
		getDailyBonuses().add(DailyRankBonus.builder()
				.day(DayOfWeek.WEDNESDAY)
				.ranks(Arrays.asList(Rank.Sorfelelos, Rank.Sormester))
				.discounts(Arrays.asList(DiscountType.ExtraBonusPoints))
				.build());

		// Csütörtökönként az ivóbajnok a legolcsóbb termékeket ingyen kapja
		// meg.
		getDailyBonuses().add(DailyRankBonus.builder()
				.day(DayOfWeek.THURSDAY)
				.ranks(Arrays.asList(Rank.Ivobajnok))
				.discounts(Arrays.asList(DiscountType.TheCheapestForFree))
				.build());

		// Szombatonként mindenkinek ingyenes szállítás van.
		getDailyBonuses().add(DailyRankBonus.builder()
				.day(DayOfWeek.SATURDAY)
				.ranks(Arrays.asList(Rank.values()))
				.discounts(Arrays.asList(DiscountType.FreeShipping))
				.build());
	}

	public DailyRankBonus(List<Rank> ranks, DayOfWeek day, List<DiscountType> discounts) {
		super();
		this.ranks = ranks;
		this.day = day;
		this.discounts = discounts;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public List<DiscountType> getDiscounts() {
		return discounts;
	}

	public List<Rank> getRanks() {
		return ranks;
	}

	public static List<DailyRankBonus> getDailyBonuses() {
		return DAILY_BONUSES;
	}

}
