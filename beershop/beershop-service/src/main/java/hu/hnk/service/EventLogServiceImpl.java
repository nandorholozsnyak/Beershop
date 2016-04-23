package hu.hnk.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.EventLogService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.interfaces.EventLogDao;

@Stateless
@Local(EventLogService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EventLogServiceImpl implements EventLogService {

	@EJB
	private EventLogDao eventLogDao;

	@Override
	public List<EventLog> findByUser(User user) {
		return eventLogDao.findByUser(user);
	}

	@Override
	public EventLog save(EventLog event) {
		return eventLogDao.save(event);
	}

	@Override
	public EventLog createEventLog(EventLogType logType, User user) {
		EventLog result = null;
		if (logType.equals(EventLogType.Registration)) {
			result = createRegistrationEventLog(user);
		} else if (logType.equals(EventLogType.MoneyTransfer)) {
			result = createMoneyTransferEventLog(user);
		}
		return result;
	}

	private EventLog createRegistrationEventLog(User user) {
		EventLog event = new EventLog();
		event.setDate(LocalDateTime.now());
		event.setAction("User registration.");
		event.setUser(user);
		return event;
	}

	private EventLog createMoneyTransferEventLog(User user) {
		EventLog event = new EventLog();
		event.setDate(LocalDateTime.now());
		event.setAction("Money transfer.");
		event.setUser(user);
		return event;
	}

}
