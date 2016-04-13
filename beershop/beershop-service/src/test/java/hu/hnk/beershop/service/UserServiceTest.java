package hu.hnk.beershop.service;

import java.util.Date;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.UsernameNotFound;
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

		userService = Mockito.spy(new UserServiceImpl());
		userDao = Mockito.mock(UserDao.class);
		userService.setUserDao(userDao);

		container = EJBContainer.createEJBContainer(p);
		container.getContext().bind("inject", this);
	}

	private UserServiceImpl userService;

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
	public void testIfUsernameAlreadyExistsReturnTrue() throws UsernameNotFound {
		User user = new User();
		user.setUsername("EmailTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		user.setDateOfBirth(new Date());
		// Mockito.when(userDao.save(user)).thenReturn(user);
		userDao.save(user);
		// Mockito.when(userDao.findUsername("EmailTest")).thenReturn("EmailTest");
		Mockito.when(userDao.findUsername("NameTest")).thenReturn("NameTest");
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
		userDao.save(user);

		Mockito.when(userDao.findUsername("EmailTest")).thenReturn("EmailTest");
		Mockito.when(userService.isUsernameAlreadyTaken("EmailTest")).thenThrow(UsernameNotFound.class);
		Assert.assertEquals(false, userService.isUsernameAlreadyTaken("EmailTest"));
	}

	@Test
	public void testIfEmailAlreadyExistsReturnTrue() throws EmailNotFound {
		User user = new User();
		user.setEmail("email@test.co");
		userDao.save(user);
		Mockito.when(userDao.findEmail("email@test.co")).thenReturn("email@test.co");
		Assert.assertEquals(true, userService.isEmailAlreadyTaken("email@test.co"));
	}

	@Test
	public void testIfEmailAlreadyExistsReturnFalse() throws EmailNotFound {

		User user = new User();
		user.setEmail("email@test.co");
		userDao.save(user);
		Mockito.when(userDao.findEmail("email@test.co")).thenReturn("email@test.co");
		Mockito.when(userService.isEmailAlreadyTaken("email@test.co")).thenThrow(EmailNotFound.class);
		Assert.assertEquals(false, userService.isEmailAlreadyTaken("email@test.co"));
	}

	@After
	public void destroy() {
		container.close();
	}

}
