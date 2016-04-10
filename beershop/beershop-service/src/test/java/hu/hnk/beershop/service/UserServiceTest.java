package hu.hnk.beershop.service;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.openejb.junit.jee.transaction.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.UserServiceImpl;

public class UserServiceTest {

	private EJBContainer container;

	private EntityManager em;
	private EntityTransaction tx;

	@Before
	public void bootContainer() throws Exception {
		final Properties p = new Properties();

		p.put("hu.neuron.java.jpa.hibernate.hbm2ddl.auto", "create");
		p.put("hu.neuron.java.jpa.hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		p.put("hu.neuron.TestDataSource", "new://Resource?type=DataSource");
		p.put("hu.neuron.TestDataSource.JtaManaged", "false");
		p.put("hu.neuron.TestDataSource.JdbcDriver", "org.hsqldb.jdbcDriver");
		p.put("hu.neuron.TestDataSource.JdbcUrl", "jdbc:hsqldb:mem:aname");
		
		container = EJBContainer.createEJBContainer(p);
		container.getContext().bind("inject", this);
	}
	
	
	@EJB
	private UserService userService;
	
	@EJB
	private UserDao userDao;
	
	private UserService userServiceMocked;
	
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
	public void testIfUsernameAlreadyExistsReturnTrue() {
		User user = new User();
		user.setUsername("EmailTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
//		user.setRank(Rank.Amatuer);
		user.setDateOfBirth(new Date());
		userDao.save(user);
		userServiceMocked = Mockito.mock(UserService.class);
		
		Mockito.when(userServiceMocked.isUsernameAlreadyTaken("NameTest")).thenReturn(true);
		Assert.assertEquals(true, userServiceMocked.isUsernameAlreadyTaken("NameTest"));
	}
	
	@Test
	public void testIfUsernameAlreadyExistsReturnFalse() {
		userServiceMocked = Mockito.mock(UserService.class);
		Mockito.when(userServiceMocked.isUsernameAlreadyTaken("NameTest")).thenReturn(false);
		Assert.assertEquals(false, userServiceMocked.isUsernameAlreadyTaken("NameTest"));
	}

	@After
	public void destroy() {
		container.close();
	}

}
