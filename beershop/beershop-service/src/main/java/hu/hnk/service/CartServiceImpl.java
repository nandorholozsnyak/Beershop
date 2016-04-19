/**
 * 
 */
package hu.hnk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.interfaces.CartDao;
import hu.hnk.interfaces.StorageDao;

/**
 * @author Nandi
 *
 */
@Stateless
@Local(CartService.class)
public class CartServiceImpl implements CartService {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(CartServiceImpl.class);

	@EJB
	CartDao cartDao;

	@EJB
	StorageDao storageDao;

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
		logger.info("Trying save items to user's cart.");
		List<CartItem> cartItems = cart.getItems();
		List<StorageItem> storageItems = storageDao.findAll();
		for (Beer beer : beersToCart.keySet()) {
			addBeerToCartItemList(beersToCart, cartItems, storageItems, beer);
		}

		cart.setItems(cartItems);
		cartDao.save(cart);
		logger.info("Items saved succesfuly to the user's cart.");
	}

	private void addBeerToCartItemList(Map<Beer, Integer> beersToCart, List<CartItem> cartItems,
			List<StorageItem> storageItems, Beer beer) {
		StorageItem beerInStorage = storageItems.stream().filter(e -> e.getBeer().equals(beer)).findFirst().get();

		CartItem item;
		CartItem foundItem;
		try {
			foundItem = cartItems.stream().filter(p -> p.getBeer().equals(beer)).findFirst().get();
		} catch (Exception e) {
			foundItem = null;
		}

		if (beersToCart.get(beer) > 0 && beerInStorage.getQuantity() > 0) {
			if (foundItem == null) {
				item = new CartItem();
				item.setAddedToCart(LocalDateTime.now());
				item.setBeer(beer);
				item.setQuantity(beersToCart.get(beer));
				item.setActive(true);
				cartItems.add(item);
			} else {
				cartItems.remove(foundItem);
				foundItem.setQuantity(foundItem.getQuantity() + beersToCart.get(beer));
				foundItem.setAddedToCart(LocalDateTime.now());
				cartDao.updateItem(foundItem);
				cartItems.add(foundItem);
			}

			beerInStorage.setQuantity(beerInStorage.getQuantity() - beersToCart.get(beer));
			storageDao.save(beerInStorage);
		}
	}

	@Override
	public Double countTotalCost(List<CartItem> cartItems) {
		return cartItems.stream()
				.mapToDouble(e->e.getBeer().getPrice()*e.getQuantity() * (100 - e.getBeer().getDiscountAmount())/100)
				.sum();
	}

}
