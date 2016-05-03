package hu.hnk.beershop.service.interfaces;

import java.util.List;

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
	 */
	public Cargo saveNewCargo(Cargo cargo, List<CartItem> items) throws DailyBuyActionLimitExceeded;

	/**
	 * Ellenõrzi hogy a paraméterként megadott felhasználónak rendelkezésére
	 * áll-e elegendõ pénz a fizetésre.
	 * 
	 * @param user
	 *            az ellenõrizendõ felhaszánló.
	 * @return igaz ha van elég pénze, hamis ha nem.
	 */
	public boolean isThereEnoughMoney(User user);
}
