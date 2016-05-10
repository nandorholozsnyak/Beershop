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
 * 
 * @author Nandi
 *
 */
public class EventLogFactory {

	private static final String MONEY_TRANSFER = "Money transfer.";
	private static final String USER_REGISTRATION = "User registration.";
	private static final String BUY_ACTION = "Buy action.";

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(EventLogFactory.class);

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
		if (logType.equals(EventLogType.Registration)) {
			result = createRegistrationEventLog(user);
		} else if (logType.equals(EventLogType.MoneyTransfer)) {
			result = createMoneyTransferEventLog(user);
		} else if (logType.equals(EventLogType.Buy)) {
			result = createBuyEventLog(user);
		}
		return result;
	}

	private static EventLog createRegistrationEventLog(User user) {
		EventLog event = createSingleEventLogForUser(user);
		event.setAction(USER_REGISTRATION);
		logger.info("Registration event log created for user " + user.getUsername());
		return event;
	}

	private static EventLog createMoneyTransferEventLog(User user) {
		EventLog event = createSingleEventLogForUser(user);
		event.setAction(MONEY_TRANSFER);
		logger.info("Money transfer event log created for user " + user.getUsername());
		return event;
	}

	private static EventLog createBuyEventLog(User user) {
		EventLog event = createSingleEventLogForUser(user);
		event.setAction(BUY_ACTION);
		logger.info("Buy event log created for user " + user.getUsername());
		return event;
	}

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
	 * @return
	 */
	public static String getUserRegistration() {
		return USER_REGISTRATION;
	}

	/**
	 * @return
	 */
	public static String getBuyAction() {
		return BUY_ACTION;
	}
}
