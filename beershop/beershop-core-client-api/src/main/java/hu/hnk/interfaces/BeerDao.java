package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Beer;

/**
 * A söröket kezelő adathozzáférési osztály implementációja.
 * 
 * A {@link Beer} entitás kezelésére jött létre, ezzel tudjuk menedzselni az
 * adatbázisban található objektumaninkat.
 * 
 * @author Nandi
 *
 */
public interface BeerDao extends BaseDao<Beer> {
	/**
	 * Az összes sör lekérdezése az adatbázisból.
	 * 
	 * @return a sörök listája
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public List<Beer> findAll() throws Exception;
}
