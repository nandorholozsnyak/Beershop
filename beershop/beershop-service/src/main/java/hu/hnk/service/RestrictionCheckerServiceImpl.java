package hu.hnk.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.service.factory.EventLogFactory;
import hu.hnk.service.tools.BuyActionRestrictions;
import hu.hnk.service.tools.MoneyTransferRestrictions;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(RestrictionCheckerService.class)
public class RestrictionCheckerServiceImpl extends UserServiceImpl implements RestrictionCheckerService {

	@EJB
	private EventLogDao eventLogDao;

	private List<MoneyTransferRestrictions> moneyRestrictions = MoneyTransferRestrictions.getMoneyRestrictions();
	private List<BuyActionRestrictions> boyActionRestrictions = BuyActionRestrictions.getRestirctedValues();

	/**
	 * Amatõr felhasználó csak napi 3-szor töltheti fel a kártyáját. Sörfelelõs
	 * napi 4-szor töltheti fel a kártyáját. Ivóbajnok napi 5-ször töltheti fel
	 * a kártyáját és kap minden feltöltés után bónusz 5%-ot.
	 */
	@Override
	public boolean checkIfUserCanTransferMoney(User user) {

		List<EventLog> userEvents = getTodayMoneyTransferEventLogs(user);
		if (userEvents == null || userEvents.size() == 0) {
			return true;
		}
		// if (userEvents.stream()
		// .count() > 3 && countRankFromXp(user).equals(Rank.Amatuer)) {
		// return false;
		// } else if (userEvents.stream()
		// .count() > 4 && countRankFromXp(user).equals(Rank.Sorfelelos)) {
		// return false;
		// } else if (userEvents.stream()
		// .count() > 5 && countRankFromXp(user).equals(Rank.Ivobajnok)) {
		// return false;
		// }

		for (MoneyTransferRestrictions mr : moneyRestrictions) {
			if (mr.getRank()
					.equals(countRankFromXp(user)) && userEvents.size() > mr.getRestrictedValue()) {
				return false;
			}
		}
		return true;

	}

	private List<EventLog> getTodayMoneyTransferEventLogs(User user) {
		if (eventLogDao.findByUser(user) == null) {
			return null;
		}
		return eventLogDao.findByUser(user)
				.stream()
				.filter(p -> p.getAction()
						.equals(EventLogFactory.getMoneyTransfer()))
				.filter(p -> p.getDate()
						.toLocalDate()
						.equals(LocalDate.now()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean checkIfUserCanBuyMoreBeer(User user) {
		List<EventLog> userEvents = getTodayBuyActionEventLogs(user);
		if (userEvents == null || userEvents.size() == 0) {
			return true;
		}

		for (BuyActionRestrictions bar : boyActionRestrictions) {
			if (bar.getRank()
					.equals(countRankFromXp(user)) && userEvents.size() > bar.getRestrictedValue()) {
				return false;
			}
		}
		return true;
	}

	private List<EventLog> getTodayBuyActionEventLogs(User user) {
		if (eventLogDao.findByUser(user) == null) {
			return null;
		}
		return eventLogDao.findByUser(user)
				.stream()
				.filter(p -> p.getAction()
						.equals(EventLogFactory.getBuyAction()))
				.filter(p -> p.getDate()
						.toLocalDate()
						.equals(LocalDate.now()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean checkIfUserCanBuyLegendBeer(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkIfUserCanPayBeers(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEventLogDao(EventLogDao eventLogDao) {
		this.eventLogDao = eventLogDao;
	}

}
