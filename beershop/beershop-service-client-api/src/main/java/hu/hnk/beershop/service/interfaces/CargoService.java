package hu.hnk.beershop.service.interfaces;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;

/**
 * @author Nandi
 *
 */
public interface CargoService {

	/**
	 * @param cargo
	 * @return
	 */
	public Cargo saveNewCargo(Cargo cargo) throws Exception;

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
