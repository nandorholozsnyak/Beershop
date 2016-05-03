package hu.hnk.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.hnk.beershop.exception.DailyBuyActionLimitExceeded;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.interfaces.CargoDao;
import hu.hnk.interfaces.CartDao;
import hu.hnk.interfaces.CartItemDao;
import hu.hnk.interfaces.EventLogDao;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.factory.EventLogFactory;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(CargoService.class)
public class CargoServiceImpl implements CargoService {
	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(CargoServiceImpl.class);

	/**
	 * A szállításokat kezelõ adathozzáférési objektum.
	 */
	@EJB
	private CargoDao cargoDao;

	/**
	 * A kosarat kezelõ adathozzáférési osztály.
	 */
	@EJB
	private CartDao cartDao;

	/**
	 * A kosarat kezelõ adathozzáférési osztály.
	 */
	@EJB
	private CartItemDao cartItemDao;

	/**
	 * A felhasználókat kezelõ adathozzáférési osztály.
	 */
	@EJB
	private UserDao userDao;

	/**
	 * Az eseményeket kezelõ adathozzáférési osztály.
	 */
	@EJB
	private EventLogDao eventLogDao;

	@EJB
	private RestrictionCheckerService restrictionCheckerService;

	@Override
	public Cargo saveNewCargo(Cargo cargo, List<CartItem> items) throws DailyBuyActionLimitExceeded {
		if (!restrictionCheckerService.checkIfUserCanBuyMoreBeer(cargo.getUser())) {
			throw new DailyBuyActionLimitExceeded("Daily buy action limit exceeded.");
		}
		// Beállítjuk a szállítmány termékeit
		Cargo savedCargo = null;
		savedCargo = saveCargo(cargo, items, savedCargo);

		// Miután mentettük a szállítást utána töröljük a felhasználó kosarából.
		deleteItemsFromUsersCart(cargo);

		// Levonjuk a felhasználótol a megrendelés árát.
		updateUsersMoneyAfterPayment(cargo);
		// Készítünk egy eventLog-ot a sikeres vásárlásról.
		createEventLogForBuyAction(cargo);

		return savedCargo;

	}

	private Cargo saveCargo(Cargo cargo, List<CartItem> items, Cargo savedCargo) {

		try {
			savedCargo = cargoDao.save(cargo);
		} catch (Exception e1) {
			logger.warn(e1);
		}
		savedCargo.setItems(items);
		try {
			cargoDao.update(savedCargo);
		} catch (Exception e1) {
			logger.warn(e1);
		}
		logger.info("Cargo saved.");
		return savedCargo;
	}

	private void createEventLogForBuyAction(Cargo cargo) {
		try {
			eventLogDao.save(EventLogFactory.createEventLog(EventLogType.Buy, cargo.getUser()));
		} catch (Exception e) {
			logger.warn(e);
		}

	}

	private void updateUsersMoneyAfterPayment(Cargo cargo) {
		cargo.getUser()
				.setMoney(cargo.getUser()
						.getMoney() - cargo.getTotalPrice());
		try {
			userDao.update(cargo.getUser());
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	private void deleteItemsFromUsersCart(Cargo cargo) {
		cargo.getItems()
				.stream()
				.forEach(p -> {
					try {
						cartItemDao.deleteItemLogically(p);
					} catch (Exception e) {
						logger.warn("Exception while trying to remove items from user's cart.");
						logger.warn(e);
					}
				});
	}

	@Override
	public boolean isThereEnoughMoney(User user) {
		return true;
	}

}
