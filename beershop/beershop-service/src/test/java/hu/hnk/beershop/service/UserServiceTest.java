package hu.hnk.beershop.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.exception.DailyMoneyTransferLimitExceededException;
import hu.hnk.beershop.exception.EmailNotFoundException;
import hu.hnk.beershop.exception.InvalidPinCodeException;
import hu.hnk.beershop.exception.UsernameNotFoundException;
import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.EventLogService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.beershop.service.restrictions.MoneyTransferRestrictions;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.factory.EventLogFactory;
import hu.hnk.service.impl.RestrictionCheckerServiceImpl;
import hu.hnk.service.impl.UserServiceImpl;

public class UserServiceTest {

	private UserServiceImpl userService;

	private UserDao userDao;

	private EventLogService eventLogService;
	private EventLogDao eventLogDao;
	private RestrictionCheckerServiceImpl res;

	@Before
	public void bootContainer() throws Exception {
		userService = Mockito.spy(new UserServiceImpl());
		userDao = Mockito.mock(UserDao.class);
		eventLogService = Mockito.mock(EventLogService.class);
		res = Mockito.spy(new RestrictionCheckerServiceImpl());
		userService.setUserDao(userDao);
		userService.setEventLogService(eventLogService);
		userService.setRestrictionCheckerService(res);
		eventLogDao = Mockito.mock(EventLogDao.class);
		res.setEventLogDao(eventLogDao);

	}

	@Test
	public void testIfDateOfBirthIsOlderThanEighteenYearsOld() {
		Date dateToBeTested = new Date(1995 - 1900, 9, 20);
		Assert.assertEquals(true, userService.isOlderThanEighteen(dateToBeTested));
	}

	@Test
	public void testIfDateOfBirthIsNotOlderThanEighteenYearsOld() {
		Date dateToBeTested = new Date(1998 - 1900, 5, 25);
		Assert.assertEquals(false, userService.isOlderThanEighteen(dateToBeTested));
	}

	@Test
	public void testIfUsernameAlreadyExistsReturnTrue() throws UsernameNotFoundException {
		User user = new User();
		user.setUsername("EmailTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		// Mockito.when(userDao.save(user)).thenReturn(user);
		// userDao.save(user);
		// Mockito.when(userDao.findUsername("EmailTest")).thenReturn("EmailTest");
		Mockito.when(userDao.findUsername("NameTest"))
				.thenReturn("NameTest");
		Assert.assertEquals(true, userService.isUsernameAlreadyTaken("NameTest"));
	}

	@Test
	public void testIfUsernameAlreadyExistsReturnFalse() throws UsernameNotFoundException {

		User user = new User();
		user.setUsername("EmailTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		// userDao.save(user);

		Mockito.when(userDao.findUsername("EmailTest"))
				.thenReturn("EmailTest");
		Mockito.when(userService.isUsernameAlreadyTaken("EmailTest"))
				.thenThrow(UsernameNotFoundException.class);
		Assert.assertEquals(false, userService.isUsernameAlreadyTaken("EmailTest"));
	}

	@Test
	public void testIfEmailAlreadyExistsReturnTrue() throws EmailNotFoundException {
		User user = new User();
		user.setEmail("email@test.co");
		/// userDao.save(user);
		Mockito.when(userDao.findEmail("email@test.co"))
				.thenReturn("email@test.co");
		Assert.assertEquals(true, userService.isEmailAlreadyTaken("email@test.co"));
	}

	@Test
	public void testIfEmailAlreadyExistsReturnFalse() throws EmailNotFoundException {

		User user = new User();
		user.setEmail("email@test.co");
		// userDao.save(user);
		Mockito.when(userDao.findEmail("email@test.co"))
				.thenReturn("email@test.co");
		Mockito.when(userService.isEmailAlreadyTaken("email@test.co"))
				.thenThrow(EmailNotFoundException.class);
		Assert.assertEquals(false, userService.isEmailAlreadyTaken("email@test.co"));
	}

	@Test(expected = InvalidPinCodeException.class)
	public void testTransferMoneyShouldThrowInvalidPinCode() throws Exception {
		String userPin = "0000";
		String expectedPin = "9999";
		Integer money = 1000;
		User loggedInUser = new User();
		loggedInUser.setMoney(0.0);
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(loggedInUser))
				.thenReturn(null);
		userService.transferMoney(userPin, expectedPin, money, loggedInUser);
	}

	@Test(expected = DailyMoneyTransferLimitExceededException.class)
	public void testTransferMoneyShouldThrowMaximumMoneyTransferLimitExceeded()
			throws Exception {
		String userPin = "9999";
		String expectedPin = "9999";
		Integer money = 1000;
		User loggedInUser = new User();
		loggedInUser.setMoney(0.0);
		List<EventLog> logs = new ArrayList<>();
		loggedInUser.setExperiencePoints(1.0);
		for (int i = 0; i < MoneyTransferRestrictions.getMoneyRestrictions()
				.stream()
				.filter(p -> p.getRank()
						.equals(userService.countRankFromXp(loggedInUser)))
				.findFirst()
				.get()
				.getRestrictedValue() + 1; i++) {
			EventLog event = new EventLog();
			event.setDate(LocalDateTime.now());
			event.setAction("Money transfer.");
			logs.add(event);
		}
		Mockito.when(eventLogDao.findByUserWhereDateIsToday(loggedInUser))
				.thenReturn(logs);
		userService.transferMoney(userPin, expectedPin, money, loggedInUser);
	}

	@Test
	public void testTransferMoney() throws Exception {
		String userPin = "0000";
		String expectedPin = "0000";
		Integer money = 1000;
		User loggedInUser = new User();
		loggedInUser.setMoney(0.0);
		Mockito.when(userDao.save(loggedInUser))
				.thenReturn(loggedInUser);
		Mockito.when(eventLogService.save(EventLogFactory.createEventLog(EventLogType.MONEYTRANSFER, loggedInUser)))
				.thenReturn(new EventLog());
		userService.transferMoney(userPin, expectedPin, money, loggedInUser);
	}

	@Test
	public void testCountExperiencePointsInPercentage() {
		Assert.assertEquals(0, (double) userService.countExperiencePointsInPercentage((double) -1), 0.0);
		Assert.assertEquals(0, (double) userService.countExperiencePointsInPercentage((double) 0), 0.0);
		Assert.assertEquals(50, (double) userService.countExperiencePointsInPercentage((double) 1250), 0.0);
		Assert.assertEquals(100, (double) userService.countExperiencePointsInPercentage((double) 2500), 0.0);
		Assert.assertEquals(0, (double) userService.countExperiencePointsInPercentage((double) 2501), 0.0);
		Assert.assertEquals(50, (double) userService.countExperiencePointsInPercentage((double) 3750), 0.0);
		Assert.assertEquals(100, (double) userService.countExperiencePointsInPercentage((double) 5000), 0.0);
		Assert.assertEquals(100, (double) userService.countExperiencePointsInPercentage((double) 5001), 0.0);
	}

	@Test
	public void testCountRankFromXp() {
		User user = new User();
		user.setExperiencePoints((double) 1);
		Assert.assertEquals(Rank.AMATUER, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 2500);
		Assert.assertEquals(Rank.AMATUER, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 2501);
		Assert.assertEquals(Rank.SORFELELOS, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 4999);
		Assert.assertEquals(Rank.SORFELELOS, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 5001);
		Assert.assertEquals(Rank.IVOBAJNOK, userService.countRankFromXp(user));
	}

	@After
	public void destroy() {
		// container.close();
	}

}
