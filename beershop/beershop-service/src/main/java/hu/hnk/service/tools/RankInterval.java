package hu.hnk.service.tools;

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

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public Integer getMinimumXP() {
		return minimumXP;
	}

	public void setMinimumXP(Integer minimumXP) {
		this.minimumXP = minimumXP;
	}

	public Integer getMaximumXP() {
		return maximumXP;
	}

	public void setMaximumXP(Integer maximumXP) {
		this.maximumXP = maximumXP;
	}

}
