package hu.hnk.beershop.dao.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.apache.openejb.junit.jee.transaction.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hu.hnk.beershop.exception.UsernameNotFoundException;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.CartDao;
import hu.hnk.interfaces.UserDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartDaoTest {

	// private EntityManager em;
	// private EntityTransaction tx;
	private static EJBContainer container;

	@EJB
	private CartDao cartDao;
	@EJB
	private UserDao userDao;

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

//	@Test
//	@Transaction(rollback = true)
	public void testSaveWithItems() throws Exception {
		List<CartItem> items = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			CartItem item = new CartItem();
			Beer beer = new Beer();
			beer.setName("test"+i);
			beer.setAlcoholLevel(10.0);
			beer.setDiscountAmount(0);
			beer.setPrice(10.1);
			
			item.setBeer(beer);
			item.setQuantity(10 * (i + 1));
			items.add(item);
		}

		User user = new User();
		user.setUsername("NameTest");
		user.setEmail("email@email.me");
		user.setPassword("ASD");
		user.setPoints((double) 150);
		user.setDateOfBirth(LocalDate.of(1995,10,20));
		user.setExperiencePoints((double) 100);
		userDao.save(user);

		User namedUser = userDao.findByUsername("NameTest");
		Cart cart = new Cart();
		cart.setUser(namedUser);
		cart.setItems(items);
		cartDao.save(cart);

		Cart asked = cartDao.findByUser(namedUser);
		asked.getItems().stream().forEach(System.out::println);

	}

	@After
	public void destroyContainer() {
		container.close();
	}

}
