package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Storage;

/**
 * @author Nandi
 *
 */
public interface StorageDao {
	
	/**
	 * A raktár információit lekérdezõ metódus.
	 * 
	 * @return a raktár információi.
	 */
	public List<Storage> findAll();
}
