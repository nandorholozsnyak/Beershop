package hu.hnk.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.interfaces.EventLogDao;

@Stateless
@Local(RestrictionCheckerService.class)
public class RestrictionCheckerServiceImpl implements RestrictionCheckerService {

	@EJB
	EventLogDao eventLogDao;

	@Override
	public boolean checkIfUserCanTransferMoney(User user) {
		List<EventLog> userEvents = eventLogDao.findByUser(user);

		return false;
	}

	@Override
	public boolean checkIfUserCanBuyMoreBeer(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkIfUserCanBuyLegendBeer(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkIfUserCanPayBeers(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
