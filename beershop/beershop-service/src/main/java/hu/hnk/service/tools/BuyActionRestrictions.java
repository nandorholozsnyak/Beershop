package hu.hnk.service.tools;

import java.util.ArrayList;
import java.util.List;

import hu.hnk.beershop.model.Rank;

public class BuyActionRestrictions {

	private Rank rank;
	private Integer restrictedValue;

	private static List<BuyActionRestrictions> restirctedValues;

	static {
		restirctedValues = new ArrayList<>();
		restirctedValues.add(new BuyActionRestrictions(Rank.Amatuer, 3));
		restirctedValues.add(new BuyActionRestrictions(Rank.Sorfelelos, 5));
		restirctedValues.add(new BuyActionRestrictions(Rank.Ivobajnok, 7));
		restirctedValues.add(new BuyActionRestrictions(Rank.Sormester, 10));
		restirctedValues.add(new BuyActionRestrictions(Rank.Sordoktor, 15));
		restirctedValues.add(new BuyActionRestrictions(Rank.Legenda, 30));
	}

	public BuyActionRestrictions(Rank rank, Integer restrictedValue) {
		super();
		this.rank = rank;
		this.restrictedValue = restrictedValue;
	}

	public Rank getRank() {
		return rank;
	}

	public Integer getRestrictedValue() {
		return restrictedValue;
	}

	public static List<BuyActionRestrictions> getRestirctedValues() {
		return restirctedValues;
	}

}
