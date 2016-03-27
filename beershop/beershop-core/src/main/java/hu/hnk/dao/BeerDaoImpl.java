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

	@PersistenceContext
	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.hnk.interfaces.BeerDao#findAll()
	 */
	@Override
	public List<Beer> findAll() {
		TypedQuery<Beer> query = em.createNamedQuery("Beer.findAll", Beer.class);
		return query.getResultList();
	}

}
