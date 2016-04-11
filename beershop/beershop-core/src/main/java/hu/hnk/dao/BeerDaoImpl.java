/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.hnk.beershop.model.Beer;
import hu.hnk.interfaces.BeerDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(BeerDao.class)
public class BeerDaoImpl implements BeerDao {
	
	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Az összes sör lekérdezése az adatbázisból.
	 * 
	 * @return a sörök listája
	 */
	@Override
	public List<Beer> findAll() {
		TypedQuery<Beer> query = em.createNamedQuery("Beer.findAll", Beer.class);
		return query.getResultList();
	}

}
