package hu.hnk.service.tools;

import java.util.ArrayList;
import java.util.List;

import hu.hnk.beershop.model.Rank;

public class MoneyTransferRestrictions {
	private Rank rank;
	private Integer restrictedValue;
	private static List<MoneyTransferRestrictions> moneyRestrictions;

	static {
		moneyRestrictions = new ArrayList<>();
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.Amatuer, 3));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.Sorfelelos, 4));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.Ivobajnok, 4));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.Sormester, 5));
		moneyRestrictions.add(new MoneyTransferRestrictions(Rank.Sordoktor, 8));
	}

	public MoneyTransferRestrictions(Rank rank, Integer restrictedValue) {
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

	public static List<MoneyTransferRestrictions> getMoneyRestrictions() {
		return moneyRestrictions;
	}

}
