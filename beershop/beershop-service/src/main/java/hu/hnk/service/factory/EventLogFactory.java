package hu.hnk.service.factory;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.logfactory.EventLogType;

/**
 * Az eseményeket létrehozó Factory osztály.
 * 
 * Egy factory osztály amely az események létrehozására jött létre, csak előre
 * definiált eseményeket tudunk létrehozni lásd: {@link EventLogType}
 * 
 * @author Nandi
 *
 */
public class EventLogFactory {

	/**
	 * A sikeres pénzfeltöltési esemény akciójaként használatos konstans.
	 */
	private static final String MONEY_TRANSFER = "Money transfer.";
	/**
	 * A sikeres regisztráció esemény akciójaként használatos konstans.
	 */
	private static final String USER_REGISTRATION = "User registration.";
	/**
	 * A sikeres vásárlás esemény akciójaként használatos konstans.
	 */
	private static final String BUY_ACTION = "Buy action.";

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(EventLogFactory.class);

	// privát konstuktor, csak statikus metódusai vannak, nem kell
	// példányosítani
	/**
	 * Privát konstuktor.
	 */
	private EventLogFactory() {
	}

	/**
	 * Egy eseménykészítő <code>Factory</code> osztály metódusa, amely
	 * eseményeket hoz létre.
	 * 
	 * Metódusként megadható az esemény típusa, amelyet az {@link EventLogType}
	 * enumeráció ír le. Szükséges az egyes felhasználói kritériumok miatt, a
	 * korlátozásokat majd ezen keresztül tudjuk vizsgnáli.
	 * 
	 * @param logType
	 *            az esemény típusa
	 * @param user
	 *            az eseményhez kötött felhasználó.
	 * @return a létrehozott esemény.
	 */
	public static EventLog createEventLog(EventLogType logType, User user) {
		EventLog result = null;
		if (logType.equals(EventLogType.REGISTRATION)) {
			result = createRegistrationEventLog(user);
		} else if (logType.equals(EventLogType.MONEYTRANSFER)) {
			result = createMoneyTransferEventLog(user);
		} else if (logType.equals(EventLogType.BUY)) {
			result = createBuyEventLog(user);
		}
		return result;
	}

	/**
	 * A paraméterül kapott felhasználóhoz létrehoz egy regisztrációs eseményt.
	 * 
	 * @param user
	 *            az eseményhez kapcsolódó felhasználó.
	 * @return a létrehozott esemény.
	 */
	private static EventLog createRegistrationEventLog(User user) {
		EventLog event = createSingleEventLogForUser(user);
		event.setAction(USER_REGISTRATION);
		logger.info("Registration event log created for user {}", user.getUsername());
		return event;
	}

	/**
	 * A paraméterül kapott felhasználóhoz létrehoz egy pénzfeltöltési eseményt.
	 * 
	 * @param user
	 *            az eseményhez kapcsolódó felhasználó.
	 * @return a létrehozott esemény.
	 */
	private static EventLog createMoneyTransferEventLog(User user) {
		EventLog event = createSingleEventLogForUser(user);
		event.setAction(MONEY_TRANSFER);
		logger.info("Money transfer event log created for user {}", user.getUsername());
		return event;
	}

	/**
	 * A paraméterül kapott felhasználóhoz létrehoz egy vásárlási eseményt.
	 * 
	 * @param user
	 *            az eseményhez kapcsolódó felhasználó.
	 * @return a létrehozott esemény.
	 */
	private static EventLog createBuyEventLog(User user) {
		EventLog event = createSingleEventLogForUser(user);
		event.setAction(BUY_ACTION);
		logger.info("Buy event log created for user {}", user.getUsername());
		return event;
	}

	/**
	 * A paraméterül kapott felhasználóhoz létrehoz eseményt.
	 * 
	 * @param user
	 *            az eseményhez kapcsolódó felhasználó.
	 * @return a létrehozott esemény.
	 */
	private static EventLog createSingleEventLogForUser(User user) {
		EventLog event = new EventLog();
		event.setDate(LocalDateTime.now());
		event.setUser(user);
		return event;
	}

	/**
	 * Visszaadja a pénzfeltöltési esemény akciójának értékét.
	 * 
	 * @return a pénzfeltöltési esemény akciójának értéke.
	 */
	public static String getMoneyTransfer() {
		return MONEY_TRANSFER;
	}

	/**
	 * Visszaadja a sikeres regszitrációt leíró esemény akcióját.
	 * 
	 * @return regisztrációt leíró esemény akciója.
	 */
	public static String getUserRegistration() {
		return USER_REGISTRATION;
	}

	/**
	 * Visszaadja a sikeres vásárlást leíró esemény akcióját.
	 * 
	 * @return vásárlást leíró esemény akciója.
	 */
	public static String getBuyAction() {
		return BUY_ACTION;
	}
}
