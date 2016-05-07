/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.Beer;
import hu.hnk.interfaces.BeerDao;

/**
 * A söröket kezelő adathozzáférési osztály implementációja.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(BeerDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BeerDaoImpl extends BaseDaoImpl<Beer> implements BeerDao {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(BeerDaoImpl.class);

	/**
	 * A söröket kezelő osztály konstuktora.
	 */
	public BeerDaoImpl() {
		super(Beer.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Beer> findAll() {
		logger.info("Getting all beers from database.");
		TypedQuery<Beer> query = entityManager.createNamedQuery("Beer.findAll", Beer.class);
		return query.getResultList();
	}

}
