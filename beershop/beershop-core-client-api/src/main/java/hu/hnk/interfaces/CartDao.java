package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;

/**
 * @author Nandi
 *
 */
public interface CartDao {

	/**
	 * Egy kosár információit lekérdezõ metódus.
	 * 
	 * @return a kosár információi.
	 */
	public List<Cart> findAll();

	/**
	 * A kosár tartalmának mentése adatbázisba.
	 * 
	 * @param cart
	 *            a mentendõ kosár.
	 * @return a mentett kosár.
	 */
	public Cart save(Cart cart);

	public Cart findByUser(User user);

	public void deleteItem(CartItem item) throws Exception;

	public CartItem updateItem(CartItem item);
	

}
