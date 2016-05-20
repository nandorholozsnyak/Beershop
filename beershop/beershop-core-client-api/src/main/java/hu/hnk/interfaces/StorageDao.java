package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;

/**
 * A raktárban található árukat kezelő adathozzáférési osztály implementációja.
 * 
 * A szóban forgó osztály a {@link StorageItem} entitásokat kezeli.
 * 
 * @author Nandi
 *
 */
public interface StorageDao extends BaseDao<StorageItem> {

	/**
	 * A raktár információit lekérdező metódus.
	 * 
	 * @return a raktár információi.
	 * @throws Exception
	 */
	public List<StorageItem> findAll() throws Exception;

	/**
	 * A paraméterként kapott raktárbeli elemeken végzett módosítások mentése az
	 * adatbázisba.
	 * 
	 * @param storage
	 *            a mentendő raktárbeli elemek listája.
	 * @throws Exception
	 */
	public void saveAllChanges(List<StorageItem> storage) throws Exception;

	/**
	 * Raktárbeli elem keresése sör alapján.
	 * 
	 * @param beer
	 *            a keresendő sör.
	 * @return a megtalált raktárbeli elem.
	 * @throws Exception
	 */
	public StorageItem findByBeer(Beer beer) throws Exception;
}
