package hu.hnk.beershop.service.interfaces;

import java.util.Date;
import java.util.List;

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetExceptionException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceededException;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.utils.PaymentMode;

/**
 * A szálítással kapcsolatos szolgáltatásokat leíró interfész.
 * 
 * @author Nandi
 *
 */
public interface CargoService {

	/**
	 * Előkészíti egy új szállítás mentését.
	 * 
	 * A megkapott <code>cargo</code> objektumot elmenti majd az elmentett
	 * objektumot ahogy visszakaptuk beállítjuk a szállítás termékeit amit a
	 * <code>items</code> paraméterben adhatunk meg. A szállítás sikeres mentése
	 * utána termékeket töröljük logikailag a felhasználó kosarából és az
	 * összeget levesszük a felhasználó számlájáról.
	 * 
	 * @param cargo
	 *            az új mentendő szállítás,
	 * @param items
	 *            a szállításhoz kapcsolódó termékek listája.
	 * @return az elmentett szállítás.
	 * @throws DailyBuyActionLimitExceededException
	 *             ha a felhasználó túllépi a napi megengedett keretet.
	 * @throws CanNotBuyLegendaryBeerYetExceptionException
	 *             ha szerepel a termékek listájában legendás termék, de a
	 *             felhasználó még nem jogosult ezek vásárlására.
	 */
	public Cargo saveNewCargo(Cargo cargo, List<CartItem> items)
			throws DailyBuyActionLimitExceededException, CanNotBuyLegendaryBeerYetExceptionException;

	/**
	 * Ellenőrzi hogy a paraméterként megadott felhasználónak rendelkezésére
	 * áll-e elegendő pénz a fizetésre.
	 * 
	 * @param totalCost
	 *            a fizetendő összeg.
	 * 
	 * @param user
	 *            az ellenőrizendő felhaszánló.
	 * @param paymentMode
	 *            a fizetés típusa
	 * @return igaz ha van elég pénze, hamis ha nem.
	 */
	public boolean isThereEnoughMoney(Double totalCost, User user, PaymentMode paymentMode);

	/**
	 * Visszaadja a rendelések/szállítások listáját felhasználó szerint.
	 * 
	 * @param user
	 *            a keresendő szállításokkal rendelkező felhasználó
	 * @return a szállítások listája
	 */
	public List<Cargo> findByUser(User user);

	/**
	 * Kiszámolja a felhasználónak a fizetés utáni megmaradandó egyenlegét.
	 * 
	 * @param totalcost
	 *            a teljes fizetendő összeg
	 * @param user
	 *            a felhasználó akinek számoljuk az összeget
	 * @param paymentMode
	 *            a fizetés típusa
	 * @return a kiszámolt összeg, azaz a <code>totalcost</code> és a
	 *         {@code User#getMoney()} különbsége, ahol a <code>user</code> a
	 *         tranzakciót végrehajtó felhasználó.
	 */
	public Double countMoneyAfterPayment(Double totalcost, User user, PaymentMode paymentMode);

	/**
	 * A szállítás elkészítését jelző metódus. Segítségével a felhasználó nyomon
	 * tudja követni mennyi idő mire a csomagját becsomagolják, minden csomag 10
	 * perc alatt készül el, ezek után a futár szállítja.
	 * 
	 * @param orderDate
	 *            a szállítás rendelésének időpontja.
	 * @return a kiszámított érték perc/másodperc formában.
	 */
	public String countdownTenMinutes(Date orderDate);

}
