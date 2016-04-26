package hu.hnk.beershop.service.interfaces;

import java.util.List;
import java.util.Map;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;

/**
 * @author Nandi
 *
 */
public interface CartService {

	/**
	 * A kosár tartalmának mentése adatbázisba az adatelérési objektumon
	 * keresztül.
	 * 
	 * @param cart
	 *            a mentendõ kosár.
	 * @return a mentett kosár.
	 */
	public Cart save(Cart cart);

	/**
	 * Kosár megkeresése felhasználó alapján.
	 * 
	 * @param user
	 *            a keresett felhasználó.
	 * @return a keresett kosár.
	 */
	public Cart findByUser(User user);

	/**
	 * Termék logikai törlése a kosárból.
	 * 
	 * @param item
	 *            a törlendõ elem.
	 * @throws Exception
	 *             bármilyen adatbázis hiba esetén.
	 */
	public void deletItemFromCart(CartItem item) throws Exception;

	/**
	 * Elemek kosárba történõ mentése.
	 * 
	 * @param beersToCart
	 *            a mentendõ sörök darabszámmal.
	 * @param cart
	 *            a felhasználó kosara.
	 */
	public void saveItemsToCart(Map<Beer, Integer> beersToCart, Cart cart);

	/**
	 * A vásárlás során fizetendõ összeg számítása.
	 * 
	 * @param cartItems
	 *            a felhasználó kosarában szereplõ termékek listája.
	 * @return a fizetendõ összeg.
	 */
	public Double countTotalCost(List<CartItem> cartItems);

	/**
	 * A bónusz pontok számítása, egy vásárlás során. A bónusz a sör
	 * alkoholtartalmának, a megrendelt darabszámból, a sör árából illetve a
	 * kedvezmény szorzataként számolódik.
	 * 
	 * @param cartItems
	 *            a kosárban levõ termékek.
	 * @return a kiszámított bónusz pontok.
	 */
	public Double countBonusPoints(List<CartItem> cartItems);

	/**
	 * @param user
	 * @param paymentMode
	 * @return
	 */
	public Double countMoneyAfterPayment(User user,Double cost, String paymentMode);

}
