package hu.hnk.beershop.service.interfaces;

import java.util.Date;
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
	 * Előkészíti egy új szállítás mentését. A megkapott <code>cargo</code>
	 * objektumot elmenti majd az elmentett objektumot ahogy visszakaptuk
	 * beállítjuk a szállítás termékeit amit a <code>items</code> paraméterben
	 * adhatunk meg. A szállítás sikeres mentése utána termékeket töröljük
	 * logikailag a felhasználó kosarából és az összeget levesszük a felhasználó
	 * számlájáról.
	 * 
	 * @param cargo
	 *            az új mentendő szállítás,
	 * @param items
	 *            a szállításhoz kapcsolódó termékek listája.
	 * @return az elmentett szállítás.
	 * @throws DailyBuyActionLimitExceeded
	 *             ha a felhasználó túllépi a napi megengedett keretet.
	 * @throws CanNotBuyLegendaryBeerYetException
	 *             ha szerepel a termékek listájában legendás termék, de a
	 *             felhasználó még nem jogosult ezek vásárlására.
	 */
	public Cargo saveNewCargo(Cargo cargo, List<CartItem> items)
			throws DailyBuyActionLimitExceeded, CanNotBuyLegendaryBeerYetException;

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználónak rendelkezésére
	 * áll-e elegendő pénz a fizetésre.
	 * 
	 * @param user
	 *            az ellenőrizendő felhaszánló.
	 * @return igaz ha van elég pénze, hamis ha nem.
	 */
	public boolean isThereEnoughMoney(Double totalCost, User user);

	public List<Cargo> findByUser(User user);

	public Double countMoneyAfterPayment(Double totalcost, User user);

	public String countdownTenMinute(Date orderDate);

}
