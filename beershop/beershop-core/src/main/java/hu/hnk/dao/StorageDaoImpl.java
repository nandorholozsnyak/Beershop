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
import javax.persistence.Query;

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
public class StorageDaoImpl implements StorageDao {

	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StorageItem> findAll() {
		Query query = em.createQuery("SELECT s FROM StorageItem s");
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAllChanges(List<StorageItem> storage) {
		storage.stream()
				.forEach(entity -> em.merge(entity));
		// for(Storage stItem : storage) {
		// em.merge(stItem);
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.hnk.interfaces.StorageDao#save(hu.hnk.beershop.model.StorageItem)
	 */
	@Override
	public StorageItem save(StorageItem storageItem) {
		return em.merge(storageItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hu.hnk.interfaces.StorageDao#findByBeer(hu.hnk.beershop.model.Beer)
	 */
	@Override
	public StorageItem findByBeer(Beer beer) {
		return em.createNamedQuery("StorageItem.findByBeer", StorageItem.class)
				.setParameter("beer", beer)
				.getSingleResult();
	}

}
