/**
 * 
 */
package hu.hnk.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.interfaces.CartDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(CartService.class)
public class CartServiceImpl implements CartService {

	@EJB
	CartDao cartDao;

	@Override
	public Cart save(Cart cart) {
		return cartDao.save(cart);
	}

	@Override
	public Cart findByUser(User user) {
		return cartDao.findByUser(user);
	}

	@Override
	public void saveItemsToCart(Map<Beer, Integer> beersToCart, Cart cart) {
		List<CartItem> cartItems = new ArrayList<>();
		for (Beer beer : beersToCart.keySet()) {
			if (beersToCart.get(beer) > 0) {
				CartItem item = new CartItem();
				item.setBeer(beer);
				item.setQuantity(beersToCart.get(beer));
				item.setAddedToCart(LocalDateTime.now());
				item.setActive(true);
				cartItems.add(item);
			}
		}

		cart.setItems(cartItems);
		System.out.println(cart);
		cartDao.save(cart);
	}

}
