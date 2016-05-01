package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;

/**
 * A raktárt kezelõ adathozzáféréi osztály interfésze.
 * 
 * @author Nandi
 *
 */
public interface StorageDao extends BaseDao<StorageItem> {

	/**
	 * A raktár információit lekérdezõ metódus.
	 * 
	 * @return a raktár információi.
	 */
	public List<StorageItem> findAll();

	/**
	 * A paraméterként kapott raktárbeli elemeken végzett módosítások mentése az
	 * adatbázisba.
	 * 
	 * @param storage
	 *            a mentendõ raktárbeli elemek listája.
	 */
	public void saveAllChanges(List<StorageItem> storage);

	/**
	 * Raktárbeli elem keresése sör alapján.
	 * 
	 * @param beer
	 *            a keresendõ sör.
	 * @return a megtalált raktárbeli elem.
	 */
	public StorageItem findByBeer(Beer beer);
}
