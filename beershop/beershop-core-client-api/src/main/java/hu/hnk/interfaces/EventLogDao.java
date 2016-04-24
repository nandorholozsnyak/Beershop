package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;

/**
 * Az eseményeket kezelõ adathozzáférési osztály interfésze.
 * @author Nandi
 *
 */
public interface EventLogDao {
	
	/**
	 * Eseménylista keresése felhasználó alapján.
	 * @param user a keresendõ események felhasználója.
	 * @return a keresett felhasználó eseménylistája.
	 */
	public List<EventLog> findByUser(User user);
	
	/**
	 * Esemény mentése.
	 * @param event a mentendõ esemény.
	 * @return a mentett esemény.
	 */
	public EventLog save(EventLog event);
	
}
