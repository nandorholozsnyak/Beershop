package hu.hnk.service.tools;

import java.util.ArrayList;
import java.util.List;

import hu.hnk.beershop.model.Rank;

/**
 * @author Nandi
 *
 */
public class RankInterval {

	public RankInterval(Rank rank, Integer minimumXP, Integer maximumXP) {
		super();
		this.rank = rank;
		this.minimumXP = minimumXP;
		this.maximumXP = maximumXP;
	}

	private Rank rank;
	private Integer minimumXP;
	private Integer maximumXP;
	private static List<RankInterval> rankIntverals;

	static {
		rankIntverals = new ArrayList<>();
		rankIntverals.add(new RankInterval(Rank.Amatuer, -1, 2500));
		rankIntverals.add(new RankInterval(Rank.Sorfelelos, 2500, 5000));
		rankIntverals.add(new RankInterval(Rank.Ivobajnok, 5000, 7500));
		rankIntverals.add(new RankInterval(Rank.Sormester, 7500, 1000));
		rankIntverals.add(new RankInterval(Rank.Sordoktor, 10000, 12500));
		rankIntverals.add(new RankInterval(Rank.Legenda, 12500, 15000));
	}

	public Rank getRank() {
		return rank;
	}

	public Integer getMinimumXP() {
		return minimumXP;
	}

	public Integer getMaximumXP() {
		return maximumXP;
	}

	public static List<RankInterval> getRankIntverals() {
		return rankIntverals;
	}

}
