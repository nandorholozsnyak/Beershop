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

/**
 * @author Nandi
 *
 */
@Stateless
@Local(EventLogService.class)
public class EventLogServiceImpl implements EventLogService {

	/**
	 * Az eseményeket kezelő adathozzáférési objektum.
	 */
	@EJB
	private EventLogDao eventLogDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EventLog> findByUser(User user) {
		return eventLogDao.findByUser(user);
	}

	/**
	 * {@inheritDoc}
	 */
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
