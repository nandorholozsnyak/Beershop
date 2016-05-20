package hu.hnk.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.EventLogDao;

/**
 * Az eseményeket kezelő adathozzáférési osztály implementációja.
 * 
 * Segítségével az {@link EventLog} entitás kezelhető.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(EventLogDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EventLogDaoImpl extends BaseDaoImpl<EventLog> implements EventLogDao {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(EventLogDaoImpl.class);

	/**
	 * Az osztály konstuktora.
	 */
	public EventLogDaoImpl() {
		super(EventLog.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EventLog> findByUser(User user) throws Exception {
		logger.info("Gettin all event logs for:" + user.getUsername());
		TypedQuery<EventLog> query = entityManager.createNamedQuery("EventLog.findByUser", EventLog.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EventLog> findByUserWhereDateIsToday(User user) throws Exception {
		logger.info("Gettin today's event logs for:" + user.getUsername());
		TypedQuery<EventLog> query = entityManager.createNamedQuery("EventLog.findByUserWhereDateIsToday",
				EventLog.class);
		query.setParameter("user", user);
		query.setParameter("startDate", LocalDate.now()
				.atStartOfDay());
		query.setParameter("endDate", LocalDate.now()
				.plus(1, ChronoUnit.DAYS)
				.atStartOfDay());
		return query.getResultList();
	}

}
