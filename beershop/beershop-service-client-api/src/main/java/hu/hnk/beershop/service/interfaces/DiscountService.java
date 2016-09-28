package hu.hnk.beershop.service.interfaces;

import java.time.LocalDate;
import java.util.List;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.discounts.DiscountType;

/**
 * Waffle miatt szerksztve.
 * @author Nandi
 *
 */
public interface DiscountService {
	/**
	 * Az elkészítesében lévő szállításra vonatkozó kedvezményeket validáló
	 * metódus.
	 * 
	 * Minden felhasználó jogosult bizonyos kedvezményekre, ezek naponta
	 * változnnak, ezeket a kedvezményeket a {@link DiscountType} enumeráció
	 * tárolja. Többféle kedvezményt tudunk validálni, ezek lehetnek százalékos
	 * kedvezmények illetve bónusz pontok formájában is megjelenhetnek.
	 * 
	 * @param discountType
	 *            a kedvezmény típusa
	 * @param cargo
	 *            az elkészítésben lévő szállítás
	 * @param today
	 *            a mai nap
	 */
	public void validateDiscount(DiscountType discountType, Cargo cargo, LocalDate today);

	/**
	 * Visszaadja a mai napi kedvezményeket.
	 * 
	 * @param user
	 *            a vizsgálandó felhasználó.
	 * @param today
	 *            a mai nap.
	 * @return az elérhető kedvezmények listája.
	 */
	public List<DiscountType> getAvailableDailyRankBonusesForUser(User user, LocalDate today);
}
