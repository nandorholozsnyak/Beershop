package hu.hnk.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.UserDao;

/**
 * A felhasználókat kezelõ adathozzáférési osztály implementációja. Enterprise
 * Java Bean
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(UserDao.class)
public class UserDaoImpl implements UserDao {
	/**
	 * Az osztály Logger-e.
	 */
	public static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	/**
	 * Az osztály entitás menedzsere.
	 */
	@PersistenceContext
	EntityManager em;

	/**
	 * Új felhasználó mentése.
	 * 
	 * @param user
	 *            az új felhasználó
	 * @return a mentett felhasználó
	 */
	public User save(User user) {
		logger.info("Felhasználó mentése.");
		return em.merge(user);
	}

	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}

	/**
	 * @param em
	 *            the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * Felhasználó keresése felhasználónév alapján.
	 * 
	 * @param username
	 *            a keresendõ felhasználó felhasználóneve.
	 * @return a megtalált felhasználó
	 */
	@Override
	public User findByUsername(String username) {
		TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
		query.setParameter("name", username);
		return query.getSingleResult();
	}

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendõ felhasználó e-mail címe.
	 * @return a megtalált felhasználó
	 */
	@Override
	public User findByEmail(String email) {
		TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}
	
	@Override
	public void remove(User user) {
		em.remove(em.contains(user) ? user : em.merge(user));	
	}

}
