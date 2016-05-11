package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.UsernameNotFound;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.UserDao;

/**
 * A felhasználókat kezelő adathozzáférési osztály implementációja. Enterprise
 * Java Bean
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(UserDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	/**
	 * Az osztály konstuktora.
	 */
	public UserDaoImpl() {
		super(User.class);
	}

	/**
	 * Az osztály Logger-e.
	 */
	public static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByUsername(String username) throws UsernameNotFound {
		logger.info("Finding user by username:" + username);
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByUsername", User.class);
		query.setParameter("name", username);
		User user;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new UsernameNotFound("There is no user with this username.");
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByEmail(String email) throws EmailNotFound {
		logger.info("Finding user by email:" + email);
		TypedQuery<User> query = entityManager.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("email", email);
		User user;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new EmailNotFound("There is no user with this e-mail.");
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(User user) {
		logger.info("Removing user:" + user.getUsername());
		entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findUsername(String username) throws UsernameNotFound {
		logger.info("Finding username:" + username);
		TypedQuery<String> query = entityManager.createNamedQuery("User.findUsername", String.class);
		query.setParameter("name", username);
		String user;
		try {
			user = query.getSingleResult();
			return user;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new UsernameNotFound("There is no user with this username.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String findEmail(String email) throws EmailNotFound {
		logger.info("Finding email:" + email);
		TypedQuery<String> query = entityManager.createNamedQuery("User.findEmail", String.class);
		query.setParameter("email", email);
		String user;
		try {
			user = query.getSingleResult();
			return user;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new EmailNotFound("There is no user with this email.");
		}
	}

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
		return query.getResultList();
	}

}
