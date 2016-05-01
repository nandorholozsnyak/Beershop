package hu.hnk.interfaces;

import java.util.List;

/**
 * @author Nandi
 *
 * @param <T>
 */
public interface BaseDao<T> {

	/**
	 * Entitás mentése.
	 * 
	 * @param entity
	 *            a mentendõ entitás.
	 * @return a mentett entitás.
	 * @throws Exception
	 *             adatbázis elérési hiba esetén.
	 */
	public T save(T entity) throws Exception;

	/**
	 * Entitás frissítése.
	 * 
	 * @param entity
	 *            a frissítendõ entitás.
	 * @throws Exception
	 *             adatbázis elérési hiba esetén.
	 */
	public void update(T entity) throws Exception;

	/**
	 * Entitás törlése.
	 * 
	 * @param id
	 *            a törlendõ entitás azonosítója.
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
