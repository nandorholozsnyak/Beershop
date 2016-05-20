package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;

/**
 * Az eseményeket kezelő adathozzáférési osztály implementációja.
 * 
 * Segítségével az {@link EventLog} entitás kezelhető.
 * 
 * @author Nandi
 *
 */
public interface EventLogDao extends BaseDao<EventLog> {

	/**
	 * Eseménylista keresése felhasználó alapján.
	 * 
	 * @param user
	 *            a keresendő események felhasználója.
	 * @return a keresett felhasználó eseménylistája.
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public List<EventLog> findByUser(User user) throws Exception;

	/**
	 * Visszaadja a paraméterként megadott felhasználónak a napi
	 * eseménylistáját.
	 * 
	 * @param user
	 *            a felhasználó,akinek az eseményeit szeretnénk listázni.
	 * @return a napi események listája.
	 * @throws Exception
	 *             adatbázis illetve más nem várt kivétel esetén
	 */
	public List<EventLog> findByUserWhereDateIsToday(User user) throws Exception;

}
