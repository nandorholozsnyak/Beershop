/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.hnk.beershop.model.Beer;
import hu.hnk.interfaces.BeerDao;

/**
 * A söröket kezelõ adathozzáférési osztály implementációja.
 * @author Nandi
 *
 */
@Stateless
@Local(BeerDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BeerDaoImpl implements BeerDao {
	
	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext
	private EntityManager em;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Beer> findAll() {
		TypedQuery<Beer> query = em.createNamedQuery("Beer.findAll", Beer.class);
		return query.getResultList();
	}

}
