package hu.hnk.dao;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import hu.hnk.beershop.model.Role;
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
	 *            a keresendő felhasználó felhasználóneve.
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
	 *            a keresendő felhasználó e-mail címe.
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

	@Override
	public User findByRole(List<Role> roleName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		cq.select(user).where(user.get("roles")).from(User.class).in(roleName);
		return (User) em.createQuery(cq).getResultList().get(0);
	}

}
