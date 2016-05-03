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

	private static final String MONEY_TRANSFER = "Money transfer.";
	private static final String USER_REGISTRATION = "User registration.";
	private static final String BUY_ACTION = "Buy action.";

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

	public static String getMoneyTransfer() {
		return MONEY_TRANSFER;
	}

	public static String getUserRegistration() {
		return USER_REGISTRATION;
	}

	public static String getBuyAction() {
		return BUY_ACTION;
	}
}
