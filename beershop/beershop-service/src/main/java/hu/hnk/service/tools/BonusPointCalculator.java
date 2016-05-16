package hu.hnk.service.tools;

import java.util.List;

import javax.ejb.Singleton;

import hu.hnk.beershop.model.CartItem;

/**
 * A bónusz pontokat számoló singleton desing pattern stílusú osztály.
 * 
 * @author Nandi
 *
 */
@Singleton
public class BonusPointCalculator {

	/**
	 * A paraméterül kapott terméklista segítéségvel számolja ki a bónusz
	 * pontokat amit a felhasználó a vásárlás során kap.
	 * 
	 * @param cartItems
	 *            a vásárolandó termékek listája.
	 * @return a kiszámolt bónusz pont.
	 */
	public Double calculate(List<CartItem> cartItems) {
		if (cartItems == null) {
			return 0.0;
		}
		return cartItems.stream()
				.mapToDouble(e -> (e.getQuantity() * e.getBeer()
						.getPrice()) / 100
						+ (e.getBeer()
								.getAlcoholLevel()
								+ e.getBeer()
										.getCapacity()
								+ e.getBeer()
										.getDiscountAmount()))
				.sum();
	}
}
