package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceeded;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;


/**
 * @author Nandi
 *
 */
public interface CargoService {

	/**
	 * @param cargo
	 * @return
	 * @throws DailyBuyActionLimitExceeded
	 * @throws CanNotBuyLegendaryBeerYetException
	 */
	public Cargo saveNewCargo(Cargo cargo, List<CartItem> items) throws DailyBuyActionLimitExceeded, CanNotBuyLegendaryBeerYetException;

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználónak rendelkezésére
	 * áll-e elegendő pénz a fizetésre.
	 * 
	 * @param user
	 *            az ellenőrizendő felhaszánló.
	 * @return igaz ha van elég pénze, hamis ha nem.
	 */
	public boolean isThereEnoughMoney(User user);
}
