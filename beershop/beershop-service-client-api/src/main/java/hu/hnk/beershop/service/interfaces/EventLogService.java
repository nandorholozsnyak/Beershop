package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;

public interface EventLogService {
	
	public List<EventLog> findByUser(User user);
	
	public EventLog save(EventLog event);
		
}
