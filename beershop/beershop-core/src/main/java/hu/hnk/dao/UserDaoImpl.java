package hu.hnk.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.hnk.beershop.modell.User;

@Stateless
@LocalBean
public class UserDao {

	@PersistenceContext
	EntityManager em;

	public void save(User user) {
		em.persist(user);
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

}
