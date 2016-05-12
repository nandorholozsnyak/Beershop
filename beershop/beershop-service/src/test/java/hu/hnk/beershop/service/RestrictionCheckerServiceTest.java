package hu.hnk.beershop.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.beershop.service.tools.BuyActionRestrictions;
import hu.hnk.beershop.service.tools.RankInterval;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.service.factory.EventLogFactory;
import hu.hnk.service.impl.RestrictionCheckerServiceImpl;

/**
 * @author Nandi
 *
 */
public class RestrictionCheckerServiceTest {

	private RestrictionCheckerServiceImpl restrictionServiceImpl;

	private EventLogDao eventLogDao;

	@Before
	public void setUp() throws Exception {
		restrictionServiceImpl = Mockito.spy(new RestrictionCheckerServiceImpl());
		eventLogDao = Mockito.mock(EventLogDao.class);
		restrictionServiceImpl.setEventLogDao(eventLogDao);
	}

	@Test
	public void testCheckIfUserCanTransferMoneyIfEventLogIsEmptyOrNull() {
		User user = new User();
		user.setExperiencePoints(0.0);
		List<EventLog> logs = new ArrayList<>();
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanTransferMoney(user));

		logs = null;
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanTransferMoney(user));

	}

	@Test
	public void testCheckIfUserCanTransferMoneyIfUserIsAmatuer() {

		User user = new User();
		user.setExperiencePoints(1.0);
		List<EventLog> logs = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertFalse(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

		logs = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertTrue(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

	}

	@Test
	public void testCheckIfUserCanTransferMoneyIfUserIsSorfelelos() {

		User user = new User();
		user.setExperiencePoints(2501.0);
		List<EventLog> logs = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertFalse(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

		logs = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertTrue(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

	}

	@Test
	public void testCheckIfUserCanTransferMoneyIfUserIsIvobajnok() {

		User user = new User();
		user.setExperiencePoints(5001.0);
		List<EventLog> logs = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertEquals(false, restrictionServiceImpl.checkIfUserCanTransferMoney(user));

		logs = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanTransferMoney(user));

	}

	@Test
	public void testCheckIfUserCanBuyMoreBeerIfEventLogIsEmpty() {
		User user = new User();
		user.setExperiencePoints(0.0);
		List<EventLog> logs = new ArrayList<>();
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanBuyMoreBeer(user));

		logs = null;
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);

		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanBuyMoreBeer(user));

	}

	@Test
	public void testCheckIfUserCanBuyMoreBeerIfUserIsAmatuer() {
		User user = new User();
		user.setExperiencePoints(1.0);
		List<EventLog> logs = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.Buy, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertEquals(false, restrictionServiceImpl.checkIfUserCanBuyMoreBeer(user));

		logs = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			logs.add(EventLogFactory.createEventLog(EventLogType.Buy, user));
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
				.thenReturn(logs);
		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanBuyMoreBeer(user));

	}

	@Test
	public void testCheckIfUserCanBuyMoreBeerForAllRank() {
		User user = new User();
		user.setExperiencePoints(0.0);
		List<EventLog> logs = new ArrayList<>();
		for (int i = 0; i < RankInterval.getRankIntverals()
				.size(); i++) {
			logs = new ArrayList<>();
			user.setExperiencePoints((double) (RankInterval.getRankIntverals()
					.get(i)
					.getMinimumXP() + 1));
			for (int j = 0; j < BuyActionRestrictions.getRestirctedValues()
					.get(i)
					.getRestrictedValue() - 1; j++) {
				logs.add(EventLogFactory.createEventLog(EventLogType.Buy, user));
			}
			Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
					.thenReturn(logs);
			Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanBuyMoreBeer(user));
		}
	}

	@Test
	public void testCheckIfUserCanNotBuyMoreBeerForAllRank() {
		User user = new User();
		user.setExperiencePoints(0.0);
		List<EventLog> logs = new ArrayList<>();
		for (int i = 0; i < RankInterval.getRankIntverals()
				.size(); i++) {
			logs = new ArrayList<>();
			user.setExperiencePoints((double) (RankInterval.getRankIntverals()
					.get(i)
					.getMinimumXP() + 1));
			for (int j = 0; j < BuyActionRestrictions.getRestirctedValues()
					.get(i)
					.getRestrictedValue() + 1; j++) {
				logs.add(EventLogFactory.createEventLog(EventLogType.Buy, user));
			}
			Mockito.when(eventLogDao.findByUserWhereDateIsToday(user))
					.thenReturn(logs);
			Assert.assertEquals(false, restrictionServiceImpl.checkIfUserCanBuyMoreBeer(user));
		}
	}

	@Test
	public void testCheckIfUserCanBuyLegendaryBeerShouldReturnTrue() {
		User user = new User();
		user.setExperiencePoints((double) RankInterval.getRankIntverals()
				.stream()
				.filter(p -> p.getRank()
						.equals(Rank.Legenda))
				.findFirst()
				.get()
				.getMinimumXP() + 1);
		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanBuyLegendBeer(user));
	}

	@Test
	public void testCheckIfUserCanBuyLegendaryBeerShouldReturnFalse() {
		User user = new User();
		Rank r;
		RankInterval.getRankIntverals()
				.stream()
				.filter(p -> !p.getRank()
						.equals(Rank.Legenda))
				.forEach(e -> {
					user.setExperiencePoints(e.getMaximumXP()
							.doubleValue());
					Assert.assertEquals(false, restrictionServiceImpl.checkIfUserCanBuyLegendBeer(user));
				});
	}

}
