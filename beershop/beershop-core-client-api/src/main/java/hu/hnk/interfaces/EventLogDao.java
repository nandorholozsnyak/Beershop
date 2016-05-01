package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;

/**
 * Az eseményeket kezelõ adathozzáférési osztály interfésze.
 * 
 * @author Nandi
 *
 */
public interface EventLogDao extends BaseDao<EventLog> {

	/**
	 * Eseménylista keresése felhasználó alapján.
	 * 
	 * @param user
	 *            a keresendõ események felhasználója.
	 * @return a keresett felhasználó eseménylistája.
	 */
	public List<EventLog> findByUser(User user);


}
