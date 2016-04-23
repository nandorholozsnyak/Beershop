package hu.hnk.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.interfaces.EventLogDao;

@Stateless
@Local(EventLogDao.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EventLogDaoImpl implements EventLogDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<EventLog> findByUser(User user) {
		TypedQuery<EventLog> query = em.createNamedQuery("EventLog.findByUser", EventLog.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	@Override
	public EventLog save(EventLog event) {
		return em.merge(event);
	}

}
