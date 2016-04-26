package hu.hnk.service;

import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.interfaces.CargoDao;

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

	@EJB
	private CargoDao cargoDao;

	@EJB
	private CartService cartService;

	@Override
	public Cargo saveNewCargo(Cargo cargo) {
		cargo.setItems(cargo.getItems()
				.stream()
				.filter(p -> p.getActive())
				.collect(Collectors.toList()));
		Cargo savedCargo = cargoDao.save(cargo);
		cargo.getItems()
				.stream()
				.forEach(p -> {
					try {
						cartService.deletItemFromCart(p);
					} catch (Exception e) {
						logger.warn("Exception while trying to remove items from user's cart.");
						logger.warn(e);
					}
				});
		logger.info("Cargo saved.");
		return savedCargo;

	}

	@Override
	public boolean isThereEnoughMoney(User user) {
		return cartService.countTotalCost(user.getCart()
				.getItems()) <= user.getMoney();
	}

}
