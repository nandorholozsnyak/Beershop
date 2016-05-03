package hu.hnk.beershop.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.service.RestrictionCheckerServiceImpl;

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
	public void testCheckIfUserCanTransferMoneyIfUserIsAmatuer() {

		User user = new User();
		user.setExperiencePoints(0.0);
		List<EventLog> logs = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			EventLog eventLog = new EventLog();
			eventLog.setAction("Money transfer.");
			eventLog.setUser(user);
			eventLog.setDate(LocalDateTime.now());
			logs.add(eventLog);
		}
		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);
		Assert.assertFalse(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

		logs = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			EventLog eventLog = new EventLog();
			eventLog.setAction("Money transfer.");
			eventLog.setUser(user);
			eventLog.setDate(LocalDateTime.now());
			logs.add(eventLog);
		}
		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);
		Assert.assertTrue(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

	}

	@Test
	public void testCheckIfUserCanTransferMoneyIfUserIsSorfelelos() {

		User user = new User();
		user.setExperiencePoints(2501.0);
		List<EventLog> logs = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			EventLog eventLog = new EventLog();
			eventLog.setAction("Money transfer.");
			eventLog.setUser(user);
			eventLog.setDate(LocalDateTime.now());
			logs.add(eventLog);
		}
		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);
		Assert.assertFalse(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

		logs = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			EventLog eventLog = new EventLog();
			eventLog.setAction("Money transfer.");
			eventLog.setUser(user);
			eventLog.setDate(LocalDateTime.now());
			logs.add(eventLog);
		}
		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);
		Assert.assertTrue(restrictionServiceImpl.checkIfUserCanTransferMoney(user));

	}

	@Test
	public void testCheckIfUserCanTransferMoneyIfUserIsIvobajnok() {

		User user = new User();
		user.setExperiencePoints(5001.0);
		List<EventLog> logs = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			EventLog eventLog = new EventLog();
			eventLog.setAction("Money transfer.");
			eventLog.setUser(user);
			eventLog.setDate(LocalDateTime.now());
			logs.add(eventLog);
		}
		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);
		Assert.assertEquals(false, restrictionServiceImpl.checkIfUserCanTransferMoney(user));

		logs = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			EventLog eventLog = new EventLog();
			eventLog.setAction("Money transfer.");
			eventLog.setUser(user);
			eventLog.setDate(LocalDateTime.now());
			logs.add(eventLog);
		}
		Mockito.when(eventLogDao.findByUser(user))
				.thenReturn(logs);
		Assert.assertEquals(true, restrictionServiceImpl.checkIfUserCanTransferMoney(user));

	}

}
