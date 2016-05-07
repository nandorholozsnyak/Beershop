package hu.hnk.service.tools;

import java.util.List;

import javax.ejb.Singleton;

import hu.hnk.beershop.model.CartItem;

@Singleton
public class BonusPointCalculator {

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
