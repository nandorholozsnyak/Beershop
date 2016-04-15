package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Storage;

/**
 * @author Nandi
 *
 */
public interface StorageService {
	
	/**
	 * A raktár információit lekérdezõ metódus.
	 * 
	 * @return a raktár információi.
	 */
	public List<Storage> findAll();
}
