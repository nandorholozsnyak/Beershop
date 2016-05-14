package hu.hnk.beershop.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.service.factory.EventLogFactory;

public class EventLogFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateRegistrationEvent() {
		User user = User.builder()
				.username("EventLoggedUser")
				.build();
		EventLog event = EventLogFactory.createEventLog(EventLogType.REGISTRATION, user);
		Assert.assertEquals(user, event.getUser());
		Assert.assertEquals(EventLogFactory.getUserRegistration(), event.getAction());
	}

	@Test
	public void testCreateBuyEvent() {
		User user = User.builder()
				.username("EventLoggedUser")
				.build();
		EventLog event = EventLogFactory.createEventLog(EventLogType.BUY, user);
		Assert.assertEquals(user, event.getUser());
		Assert.assertEquals(EventLogFactory.getBuyAction(), event.getAction());
	}

	@Test
	public void testCreateMoneyTransferEvent() {
		User user = User.builder()
				.username("EventLoggedUser")
				.build();
		EventLog event = EventLogFactory.createEventLog(EventLogType.MONEYTRANSFER, user);
		Assert.assertEquals(user, event.getUser());
		Assert.assertEquals(EventLogFactory.getMoneyTransfer(), event.getAction());
	}

}
