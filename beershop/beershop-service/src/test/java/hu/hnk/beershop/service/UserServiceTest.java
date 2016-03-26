package hu.hnk.beershop.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.service.UserServiceImpl;

public class UserServiceTest {

	private EJBContainer container;

	@Before
	public void bootContainer() throws Exception {
		container = EJBContainer.createEJBContainer();
		container.getContext().bind("inject", this);
	}

	@EJB
	private UserService userService;

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

	@After
	public void destroy() {
		container.close();
	}

}
