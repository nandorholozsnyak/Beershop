package hu.hnk.beershop.dao.test;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.openejb.junit.jee.transaction.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.UserDao;

public class UserDaoTest {

	private EntityManager em;
	private EntityTransaction tx;
	private static EJBContainer container;

	@BeforeClass
	public static void initEM() throws NamingException {
		// this.em =
		// Persistence.createEntityManagerFactory("BeerShopUnitTest").createEntityManager();
		// this.tx = this.em.getTransaction();
	}

	@Before
	public void setUp() throws NamingException {
		container = EJBContainer.createEJBContainer();
		container.getContext().bind("inject", this);
	}

	@EJB
	private UserDao userDao;

	@Test
	@Transaction(rollback = true)
	public void testUserDao() {

		User user = new User();
		user.setUsername("NameTest");
		user.setEmail("email@test.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		user.setRank(Rank.Amatuer);
		user.setDateOfBirth(new Date());
		userDao.save(user);

		User namedUser = userDao.findByUsername("NameTest");
		Assert.assertEquals("NameTest", namedUser.getUsername());

		User emailedUser = userDao.findByEmail("email@test.me");
		Assert.assertEquals("email@test.me", emailedUser.getEmail());
	}

	@After
	public void destroyContainer() {
		container.close();
	}

}
