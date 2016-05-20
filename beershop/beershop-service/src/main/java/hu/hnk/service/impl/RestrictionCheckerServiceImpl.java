package hu.hnk.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.beershop.service.restrictions.BuyActionRestrictions;
import hu.hnk.beershop.service.restrictions.MoneyTransferRestrictions;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.service.factory.EventLogFactory;

/**
 * A felhasználó tevékenységeket korlátozó interfész.
 * 
 * Minden felhasználónak be kell tartania bizonyos szabályokat, rangtól függően
 * vásárolhat napi szinten, ezeket a korlátozásokat a
 * {@link BuyActionRestrictions} osztály írja le.
 * 
 * A pénzfeltöltéssel kapcsolatban ugyan ilyen szabályok élnek amiket a
 * {@link MoneyTransferRestrictions} osztály definiál.
 * 
 * Létezik egy bizonyos típusa a söröknek amelyeket csak <b>Legendás</b> néven
 * hívunk, ezeket csak a {@link Rank#LEGENDA} ranggal rendelkező felhasználó
 * vásárolhatja meg.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(RestrictionCheckerService.class)
public class RestrictionCheckerServiceImpl extends UserServiceImpl implements RestrictionCheckerService {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(RestrictionCheckerServiceImpl.class);

	/**
	 * Az eseményeket kezelő adathozzáférési objektum.
	 */
	@EJB
	private EventLogDao eventLogDao;

	/**
	 * A pénzefeltöltéssel kapcsolatos korlátozásokat tartalmazó lista.
	 */
	private List<MoneyTransferRestrictions> moneyRestrictions = MoneyTransferRestrictions.getMoneyRestrictions();
	/**
	 * A vásárlással kapcsolatos korlátozásokat tartalmazó lista.
	 */
	private List<BuyActionRestrictions> buyActionRestrictions = BuyActionRestrictions.getRestirctedValues();

	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	@Override
	public boolean checkIfUserCanTransferMoney(User user) throws Exception {

		List<EventLog> userEvents = getTodayMoneyTransferEventLogs(user);
		if (userEvents == null || userEvents.isEmpty()) {
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

	/**
	 * Visszaadja a paraméterül adott <code>user</code> mai napi
	 * pénzfeltöltéssel kapcsolatos eseményeit.
	 * 
	 * @param user
	 *            a lekérdezendő eseményekkel kapcsolatos felhasználó.
	 * @return az események listája.
	 * @throws Exception
	 */
	private List<EventLog> getTodayMoneyTransferEventLogs(User user) throws Exception {
		if (eventLogDao.findByUserWhereDateIsToday(user) == null) {
			return null;
		}
		return eventLogDao.findByUserWhereDateIsToday(user)
				.stream()
				.filter(p -> p.getAction()
						.equals(EventLogFactory.getMoneyTransfer()))
				.filter(p -> p.getDate()
						.toLocalDate()
						.equals(LocalDate.now()))
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkIfUserCanBuyMoreBeer(User user) throws Exception {
		List<EventLog> userEvents = getTodayBuyActionEventLogs(user);
		if (userEvents == null || userEvents.size() == 0) {
			return true;
		}

		for (BuyActionRestrictions bar : buyActionRestrictions) {
			if (bar.getRank()
					.equals(countRankFromXp(user)) && userEvents.size() > bar.getRestrictedValue()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Visszaadja a paraméterül adott <code>user</code> mai napi vásárlással
	 * kapcsolatos eseményeit.
	 * 
	 * @param user
	 *            a lekérdezendő eseményekkel kapcsolatos felhasználó.
	 * @return az események listája.
	 * @throws Exception
	 */
	private List<EventLog> getTodayBuyActionEventLogs(User user) throws Exception {
		if (eventLogDao.findByUserWhereDateIsToday(user) == null) {
			return null;
		}
		return eventLogDao.findByUserWhereDateIsToday(user)
				.stream()
				.filter(p -> p.getAction()
						.equals(EventLogFactory.getBuyAction()))
				.filter(p -> p.getDate()
						.toLocalDate()
						.equals(LocalDate.now()))
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkIfUserCanBuyLegendBeer(User user) {
		if (countRankFromXp(user).equals(Rank.LEGENDA)) {
			logger.info("User can buy legendary beers.");
			return true;
		}
		logger.info("User can NOT buy legendary beers.");
		return false;
	}

	/**
	 * Beállítja az eseményeket kezelő adathozzáférési objektumot.
	 * 
	 * @param eventLogDao
	 *            az adathozzáférési objektum.
	 */
	public void setEventLogDao(EventLogDao eventLogDao) {
		this.eventLogDao = eventLogDao;
	}

}
