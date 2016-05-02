package hu.hnk.beershop.service;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.InvalidPinCode;
import hu.hnk.beershop.exception.UsernameNotFound;
import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.EventLogService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.UserServiceImpl;
import hu.hnk.service.factory.EventLogFactory;

public class UserServiceTest {

	private UserServiceImpl userService;

	private UserDao userDao;

	private EventLogService eventLogService;

	@Before
	public void bootContainer() throws Exception {
		userService = Mockito.spy(new UserServiceImpl());
		userDao = Mockito.mock(UserDao.class);
		eventLogService = Mockito.mock(EventLogService.class);
		userService.setUserDao(userDao);
		userService.setEventLogService(eventLogService);
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
	public void testIfUsernameAlreadyExistsReturnTrue() throws UsernameNotFound {
		User user = new User();
		user.setUsername("EmailTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		user.setDateOfBirth(new Date());
		// Mockito.when(userDao.save(user)).thenReturn(user);
		// userDao.save(user);
		// Mockito.when(userDao.findUsername("EmailTest")).thenReturn("EmailTest");
		Mockito.when(userDao.findUsername("NameTest"))
				.thenReturn("NameTest");
		Assert.assertEquals(true, userService.isUsernameAlreadyTaken("NameTest"));
	}

	@Test
	public void testIfUsernameAlreadyExistsReturnFalse() throws UsernameNotFound {

		User user = new User();
		user.setUsername("EmailTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		user.setDateOfBirth(new Date());
		// userDao.save(user);

		Mockito.when(userDao.findUsername("EmailTest"))
				.thenReturn("EmailTest");
		Mockito.when(userService.isUsernameAlreadyTaken("EmailTest"))
				.thenThrow(UsernameNotFound.class);
		Assert.assertEquals(false, userService.isUsernameAlreadyTaken("EmailTest"));
	}

	@Test
	public void testIfEmailAlreadyExistsReturnTrue() throws EmailNotFound {
		User user = new User();
		user.setEmail("email@test.co");
		/// userDao.save(user);
		Mockito.when(userDao.findEmail("email@test.co"))
				.thenReturn("email@test.co");
		Assert.assertEquals(true, userService.isEmailAlreadyTaken("email@test.co"));
	}

	@Test
	public void testIfEmailAlreadyExistsReturnFalse() throws EmailNotFound {

		User user = new User();
		user.setEmail("email@test.co");
		// userDao.save(user);
		Mockito.when(userDao.findEmail("email@test.co"))
				.thenReturn("email@test.co");
		Mockito.when(userService.isEmailAlreadyTaken("email@test.co"))
				.thenThrow(EmailNotFound.class);
		Assert.assertEquals(false, userService.isEmailAlreadyTaken("email@test.co"));
	}

	@Test(expected = InvalidPinCode.class)
	public void testTransferMoneyShouldThrowInvalidPinCode() throws InvalidPinCode {
		String userPin = "0000";
		String expectedPin = "9999";
		Integer money = 1000;
		User loggedInUser = new User();
		loggedInUser.setMoney(0.0);
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
		Mockito.when(eventLogService.save(EventLogFactory.createEventLog(EventLogType.MoneyTransfer, loggedInUser)))
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
		user.setExperiencePoints((double) 0);
		Assert.assertEquals(Rank.Amatuer, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 2500);
		Assert.assertEquals(Rank.Amatuer, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 2501);
		Assert.assertEquals(Rank.Sorfelelos, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 4999);
		Assert.assertEquals(Rank.Sorfelelos, userService.countRankFromXp(user));
		user.setExperiencePoints((double) 5001);
		Assert.assertEquals(Rank.Ivobajnok, userService.countRankFromXp(user));
	}

	@After
	public void destroy() {
		// container.close();
	}

}
