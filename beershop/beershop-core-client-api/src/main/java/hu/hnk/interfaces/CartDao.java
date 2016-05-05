package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;


/**
 * A kosarat kezelő adathozzáférési osztály interfésze.
 * 
 * @author Nandi
 *
 */
public interface CartDao extends BaseDao<Cart> {

	/**
	 * Egy kosár információit lekérdező metódus.
	 * 
	 * @return a kosár információi.
	 */
	public List<Cart> findAll();

	/**
	 * Kosár keresése felhasználó alapján.
	 * 
	 * @param user
	 *            a keresendő kosár birtokosa.
	 * @return a megtalált kosár.
	 */
	public Cart findByUser(User user);

}
