package hu.hnk.service;

import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.CargoService;
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

	@Override
	public Cargo saveNewCargo(Cargo cargo) {
		// User user = cargo.getUser();
		// Beállítjuk a szállítmány termékeit, de csak azokat amelyek aktívak.
		cargo.setItems(cargo.getItems()
				.stream()
				.filter(p -> p.getActive())
				.collect(Collectors.toList()));

		Cargo savedCargo = null;
		try {
			savedCargo = cargoDao.save(cargo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("Cargo saved.");
		// Miután mentettük a szállítást utána töröljük a felhasználó kosarából.
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

		// Levonjuk a felhasználótol a megrendelés árát.
		cargo.getUser()
				.setMoney(cargo.getUser()
						.getMoney() - cargo.getTotalPrice());
		try {
			userDao.update(cargo.getUser());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			eventLogDao.save(EventLogFactory.createEventLog(EventLogType.Buy, cargo.getUser()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savedCargo;

	}

	private void filterActiveItemsFromUsersCart(Cargo cargo) {

	}

	private void deleteItemsFromUserCartAfterSuccesfulPayment(Cargo cargo) {

	}

	private void createEventLogForUser(User user) {

	}

	private void setUserMoneyAfterCargoSetup(Cargo cargo, User user) {

	}

	@Override
	public boolean isThereEnoughMoney(User user) {
		return true;
	}

}
