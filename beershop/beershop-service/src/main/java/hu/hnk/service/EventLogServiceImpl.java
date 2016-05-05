package hu.hnk.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.EventLogService;
import hu.hnk.interfaces.EventLogDao;


@Stateless
@Local(EventLogService.class)
public class EventLogServiceImpl implements EventLogService {

	@EJB
	private EventLogDao eventLogDao;

	@Override
	public List<EventLog> findByUser(User user) {
		return eventLogDao.findByUser(user);
	}

	@Override
	public EventLog save(EventLog event) {
		try {
			return eventLogDao.save(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event;
	}

	

}
