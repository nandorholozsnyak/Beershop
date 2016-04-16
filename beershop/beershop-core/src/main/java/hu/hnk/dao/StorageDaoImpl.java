/**
 * 
 */
package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import hu.hnk.beershop.model.StorageItem;
import hu.hnk.interfaces.StorageDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(StorageDao.class)
public class StorageDaoImpl implements StorageDao {

	/**
	 * JPA Entity Manager.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * A raktár információit lekérdezõ metódus.
	 * 
	 * @return a raktár információi.
	 */
	@Override
	public List<StorageItem> findAll() {
		Query query = em.createQuery("SELECT s FROM StorageItem s");
		return query.getResultList();
	}

	@Override
	public void saveAllChanges(List<StorageItem> storage) {
		storage.stream().forEach(entity -> em.merge(entity));
//		for(Storage stItem : storage) {
//			em.merge(stItem);
//		}
	}

}
