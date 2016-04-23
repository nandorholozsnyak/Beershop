package hu.hnk.service.factory;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.logfactory.EventLogType;

/**
 * Az eseményeket létrehozó Factory osztály.
 * 
 * @author Nandi
 *
 */
public class EventLogFactory {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(EventLogFactory.class);

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
		EventLog event = new EventLog();
		event.setDate(LocalDateTime.now());
		event.setAction("User registration.");
		event.setUser(user);
		logger.info("Registration event log created for user " + user.getUsername());
		return event;
	}

	private static EventLog createMoneyTransferEventLog(User user) {
		EventLog event = new EventLog();
		event.setDate(LocalDateTime.now());
		event.setAction("Money transfer.");
		event.setUser(user);
		logger.info("Money transfer event log created for user " + user.getUsername());
		return event;
	}

	private static EventLog createBuyEventLog(User user) {
		EventLog event = new EventLog();
		event.setDate(LocalDateTime.now());
		event.setAction("Buy action.");
		event.setUser(user);
		logger.info("Buy event log created for user " + user.getUsername());
		return event;
	}
}
