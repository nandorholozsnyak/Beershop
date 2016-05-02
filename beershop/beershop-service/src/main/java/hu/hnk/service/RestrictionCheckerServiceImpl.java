package hu.hnk.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import hu.hnk.beershop.model.EventLog;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.interfaces.EventLogDao;

@Stateless
@Local(RestrictionCheckerService.class)
public class RestrictionCheckerServiceImpl extends UserServiceImpl implements RestrictionCheckerService {

	@EJB
	EventLogDao eventLogDao;

	@Override
	public boolean checkIfUserCanTransferMoney(User user) {

		List<EventLog> userEvents = eventLogDao.findByUser(user)
				.stream()
				.filter(p -> p.getAction()
						.equals("Money transfer."))
				.collect(Collectors.toList());

		if (userEvents.stream()
				.count() > 4 && countRankFromXp(user).equals(Rank.Amatuer)) {
			return false;
		}

		return true;

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
