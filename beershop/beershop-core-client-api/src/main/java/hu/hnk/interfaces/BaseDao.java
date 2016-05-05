package hu.hnk.interfaces;


/**
 * A bázis adahozzáférési osztály interfésze.
 * 
 * @author Nandi
 * @param <T>
 */
public interface BaseDao<T> {

	/**
	 * Entitás mentése.
	 * 
	 * @param entity
	 *            a mentendő entitás.
	 * @return a mentett entitás.
	 * @throws Exception
	 *             adatbázis elérési hiba esetén.
	 */
	public T save(T entity) throws Exception;

	/**
	 * Entitás frissítése.
	 * 
	 * @param entity
	 *            a frissítendő entitás.
	 * @throws Exception
	 *             adatbázis elérési hiba esetén.
	 */
	public void update(T entity) throws Exception;

	/**
	 * Entitás törlése.
	 * 
	 * @param id
	 *            a törlendő entitás azonosítója.
	 * @throws Exception
	 *             adatbázis elérési hiba esetén.
	 */
	public void delete(Long id) throws Exception;

	/**
	 * Entitás keresése azonosító alapján.
	 * 
	 * @param id
	 *            a keresett entitás azonosítója.
	 * @return a megtalált entitás.
	 * @throws Exception
	 *             adatbázis elérési hiba esetén.
	 */
	public T find(Long id) throws Exception;

}
