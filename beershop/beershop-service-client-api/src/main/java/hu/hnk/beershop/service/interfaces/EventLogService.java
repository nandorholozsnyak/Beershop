package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.logfactory.EventLogType;

/**
 * Az eseményeket kezelő szolgáltatás.
 * 
 * Az események listázásért illetve mentéséért felelős szolgáltatás. Az
 * eseményeknek különböző fajtáik vannak.
 * 
 * {@link EventLogType#Registration} - regisztrációs esemény amely egy új
 * felhasználó regisztrációja során mentődik el automatikusan.
 * 
 * {@link EventLogType#MoneyTransfer} - egy pénzfeltöltési esemény során
 * létrejövő felhasználó esemény.
 * 
 * {@link EventLogType#Buy} - egy vásárlás során létrejövő felhasználó esemény.
 * 
 * Az eseményeket szolgáltató <code>Factory</code> osztály az
 * <p>
 * EventLogFactory
 * </p>
 * osztály amely a
 * <p>
 * service
 * </p>
 * modulban található.
 * 
 * @author Nandi
 *
 */
public interface EventLogService {

	/**
	 * Egy adott felhasználóhoz kapcsolódó mindig az aznapi eseményeket
	 * visszaadó metódus.
	 * 
	 * @param user
	 *            a felhasználó akinek az eseményeit vissza szeretnénk adni.
	 * @return az eseményeket tartalmazó lista.
	 */
	public List<EventLog> findByUser(User user);

	/**
	 * Egy esemény mentése.
	 * 
	 * @param event
	 *            az mentendő esemény.
	 * @return a mentett esemény.
	 */
	public EventLog save(EventLog event);

}
