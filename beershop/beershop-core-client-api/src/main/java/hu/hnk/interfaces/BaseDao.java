package hu.hnk.interfaces;

import hu.hnk.beershop.model.BaseEntity;

/**
 * A bázis adahozzáférési osztály interfésze.
 * 
 * Megvalósítja az alapvető adatbázis hozzáférési műveleteket:
 * <ul>
 * <li>{@link BaseDao#save(Object)} - egy új entitás mentése az adatbázisba</li>
 * <li>{@link BaseDao#update(Object)} - már egy meglévő entitás frissítése</li>
 * <li>{@link BaseDao#delete(Long)} - egy meglévő entitás törlés, paramétere az
 * entitás azonosítója {@link BaseDao#find(Long)} - entitás keresése az
 * azonosítója alapján.</li>
 * </ul>
 * 
 * @author Nandi
 * @param <T>
 *            a kezelendő entitás osztálya
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
