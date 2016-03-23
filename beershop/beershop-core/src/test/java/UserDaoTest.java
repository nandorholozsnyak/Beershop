import static org.junit.Assert.fail;

import javax.ejb.EJB;

import org.junit.Before;
import org.junit.Test;

import hu.hnk.beershop.modell.User;
import hu.hnk.dao.UserDao;

public class UserDaoTest {
	
	@EJB
	UserDao userDao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void userDaoSavUserTest() {
		User user = new User();
		user.setUsername("Nandi");
		user.setPassword("ASD");
		userDao.save(user);
	}

}
