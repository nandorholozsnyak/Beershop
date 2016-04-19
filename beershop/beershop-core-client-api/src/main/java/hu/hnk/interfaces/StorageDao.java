package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.StorageItem;

/**
 * @author Nandi
 *
 */
public interface StorageDao {

	/**
	 * A raktár információit lekérdező metódus.
	 * 
	 * @return a raktár információi.
	 */
	public List<StorageItem> findAll();

	public void saveAllChanges(List<StorageItem> storage);

	public StorageItem save(StorageItem storageItem);
}
