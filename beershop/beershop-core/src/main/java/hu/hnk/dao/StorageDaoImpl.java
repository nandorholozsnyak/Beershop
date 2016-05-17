/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.interfaces.StorageDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(StorageDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StorageDaoImpl extends BaseDaoImpl<StorageItem> implements StorageDao {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(StorageDaoImpl.class);

	/**
	 * Az osztály konstuktora.
	 */
	public StorageDaoImpl() {
		super(StorageItem.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StorageItem> findAll() {
		logger.info("Gettin all storage items.");
		Query query = entityManager.createQuery("SELECT s FROM StorageItem s");
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAllChanges(List<StorageItem> storage) {
		logger.info("Saving all storage item changes.");
		storage.stream()
				.forEach(entity -> {
					try {
						update(entity);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StorageItem findByBeer(Beer beer) {
		logger.info("Finding storage item by beer:" + beer.getName());
		return entityManager.createNamedQuery("StorageItem.findByBeer", StorageItem.class)
				.setParameter("beer", beer)
				.getSingleResult();
	}

}
