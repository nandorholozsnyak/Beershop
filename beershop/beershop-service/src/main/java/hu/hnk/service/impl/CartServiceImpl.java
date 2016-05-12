/**
 * 
 */
package hu.hnk.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.interfaces.CartDao;
import hu.hnk.interfaces.CartItemDao;
import hu.hnk.interfaces.StorageDao;
import hu.hnk.service.cobertura.annotation.CoverageIgnore;
import hu.hnk.service.tools.BonusPointCalculator;

/**
 * A felhasználók kosarait kezelő szolgáltatás. Amikor a felhasználó hozzáad egy
 * elemet a kosarához a {@link CartService#saveItemsToCart(Map, Cart)} metódus
 * fogja ezt a kosarába pakolni.
 * 
 * @author Nandi
 *
 */
@Stateless
@Local(CartService.class)
public class CartServiceImpl implements CartService {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	/**
	 * A kosárműveletek végző adathozzáférési objektumn.
	 */
	@EJB
	private CartDao cartDao;

	/**
	 * A kosarat kezelő adathozzáférési osztály.
	 */
	@EJB
	private CartItemDao cartItemDao;

	/**
	 * A raktárt kezelő adathozzáférési objektum.
	 */
	@EJB
	private StorageDao storageDao;

	@EJB
	BonusPointCalculator calculator;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CoverageIgnore
	public Cart save(Cart cart) {
		try {
			return cartDao.save(cart);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return cart;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CoverageIgnore
	public Cart findByUser(User user) {
		return cartDao.findByUser(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveItemsToCart(Map<Beer, Integer> beersToCart, Cart cart) {
		logger.info("Trying save items to user's cart.");
		List<CartItem> cartItems = cart.getItems();
		List<StorageItem> storageItems = storageDao.findAll();
		for (Beer beer : beersToCart.keySet()) {
			addBeerToCartItemList(beersToCart, cartItems, storageItems, beer);
		}

		cart.setItems(cartItems);
		try {
			cartDao.update(cart);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Items saved succesfuly to the user's cart.");
	}

	/**
	 * A kiválasztott sör kosárba való helyezése. A metódus ellenőrzi hogy a
	 * kiválaszott sör szerepel-e a raktárban a
	 * {@link CartServiceImpl#findBeerInStorage(List, Beer)} metódus
	 * segítségével, amint ez megtörtént ellenőrzi hogy a választott sör
	 * szerepel-e már a felhasználó kosarában a
	 * {@link CartServiceImpl#findBeerInUsersCart(List, Beer)} metódus
	 * meghívásával. Amint adottak a feltételek megnézzük mennyi sört kell
	 * berakni a kosárba, megtörténik a tranzakció.
	 * 
	 * @param beersToCart
	 *            a sörök Map-je darabszámmal együtt.
	 * @param cartItems
	 *            a már kosárban levő termékek listája.
	 * @param storageItems
	 *            a raktárban szereplő termékek listája.
	 * @param beer
	 *            a kiválasztott sör.
	 */
	private void addBeerToCartItemList(Map<Beer, Integer> beersToCart, List<CartItem> cartItems,
			List<StorageItem> storageItems, Beer beer) {

		StorageItem beerInStorage = null;

		// Előbb megkeressük a sört a raktárból.
		try {
			beerInStorage = findBeerInStorage(storageItems, beer);
		} catch (NoSuchElementException e) {
			logger.warn("Beer has not been found in the storage.");
			logger.error(e.getMessage(), e);
		}

		CartItem item;
		CartItem foundItem;

		// Megnézzük hogy a választott sör szerepel-e már a felhasználó
		// kosarában.
		try {
			foundItem = findBeerInUsersCart(cartItems, beer);
		} catch (NoSuchElementException e) {
			foundItem = null;
			logger.info("Beer has not found in the user's cart.");
			logger.error(e.getMessage(), e);
		}

		// Leellenőrizzük hogy a sör létezik-e raktárban.
		if (beerInStorage != null) {
			if (beersToCart.get(beer) > 0 && beerInStorage.getQuantity() > 0) {
				if (foundItem == null) {
					item = new CartItem();
					item.setAddedToCart(LocalDateTime.now());
					item.setBeer(beer);
					item.setQuantity(beersToCart.get(beer));
					item.setActive(true);
					try {
						cartItemDao.save(item);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					cartItems.add(item);
					logger.info("New item added to user's cart list.");
				} else {
					cartItems.remove(foundItem);
					foundItem.setQuantity(foundItem.getQuantity() + beersToCart.get(beer));
					foundItem.setAddedToCart(LocalDateTime.now());
					try {
						cartItemDao.update(foundItem);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					cartItems.add(foundItem);
					logger.info("Found item updated in user's cart.");
				}

				beerInStorage.setQuantity(beerInStorage.getQuantity() - beersToCart.get(beer));
				try {
					storageDao.save(beerInStorage);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			}
		}
	}

	/**
	 * Ellenőrzi hogy a sör (<code>beer</code>) szerepel-e már a felhasználó
	 * kosarában.
	 * 
	 * @param cartItems
	 *            a felhasználó kosarában meglévő termékek.
	 * @param beer
	 *            a kiválasztott sör.
	 * @return visszaadja a terméket ha már szerepel a felhasználó kosarában.
	 * @throws NoSuchElementException
	 *             ha nem szerepel a termék a felhasználó kosarában.
	 */
	private CartItem findBeerInUsersCart(List<CartItem> cartItems, Beer beer) throws NoSuchElementException {
		return cartItems.stream()
				.filter(p -> p.getBeer()
						.equals(beer) && p.getActive())
				.findFirst()
				.get();
	}

	/**
	 * Ellenőrzi hogy a sör (<code>beer</code>) létezik-e a raktárban.
	 * 
	 * @param storageItems
	 *            a raktárban szereplő termékek listája.
	 * @param beer
	 *            az ellenőrizendő sör.
	 * @return a megtalált termék.
	 * @throws NoSuchElementException
	 *             ha a termék nem szerepel a raktárban.
	 */
	private StorageItem findBeerInStorage(List<StorageItem> storageItems, Beer beer) throws NoSuchElementException {
		return storageItems.stream()
				.filter(e -> e.getBeer()
						.equals(beer))
				.findFirst()
				.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double countTotalCost(List<CartItem> cartItems) {
		return cartItems.stream()
				.filter(p -> p.getActive())
				.mapToDouble(e -> e.getBeer()
						.getPrice() * e.getQuantity()
						* (100 - e.getBeer()
								.getDiscountAmount())
						/ 100)
				.sum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletItemFromCart(CartItem item) throws Exception {
		try {
			logger.info("Trying to delete item from cart.");
			StorageItem stItem = storageDao.findByBeer(item.getBeer());
			stItem.setQuantity(stItem.getQuantity() + item.getQuantity());
			cartItemDao.deleteItemLogically(item);
		} catch (Exception e) {
			logger.warn("Error while trying to delete item from user's cart.", e);
			throw new Exception("We were not able to delete the item from the user's cart.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double countBonusPoints(List<CartItem> cartItems) {
		return calculator.calculate(cartItems);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double countMoneyAfterPayment(User user, Double cost, String paymentMode) {
		Double result = (double) 0;
		if ("money".equals(paymentMode)) {
			result = user.getMoney() - cost;
		} else if ("bonusPoint".equals(paymentMode)) {
			result = user.getPoints() - cost;
		}
		return result;
	}

	/**
	 * Beállítja a kosarat kezelő adathozzáférési objektumát.
	 * 
	 * @param cartDao
	 *            a beállítandó adathozzáférési osztály.
	 */
	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}

	/**
	 * Beállítja a kosárban / szállításban szereplő termékek adathozzáférési
	 * objektumát.
	 * 
	 * @param cartItemDao
	 *            az adathozzáférési objektum.
	 */
	public void setCartItemDao(CartItemDao cartItemDao) {
		this.cartItemDao = cartItemDao;
	}

	/**
	 * Beállítja a raktárt kezelő adathozzáférési objektumát.
	 * 
	 * @param storageDao
	 *            az adathozzáférési objektum.
	 */
	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	}

	public void setCalculator(BonusPointCalculator calculator) {
		this.calculator = calculator;
	}

}
