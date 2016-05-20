package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.User;

/**
 * A kosarakat kezelő adathozzáférési osztály implementációja.
 * 
 * Az adatbázis hozzáférési osztály kezeli a {@link Cart} entitást.
 * 
 * @author Nandi
 *
 */
public interface CartDao extends BaseDao<Cart> {

	/**
	 * Egy kosár információit lekérdező metódus.
	 * 
	 * @return a kosár információi.
	 * @throws Exception
	 */
	public List<Cart> findAll() throws Exception;

	/**
	 * Kosár keresése felhasználó alapján.
	 * 
	 * @param user
	 *            a keresendő kosár birtokosa.
	 * @return a megtalált kosár.
	 * @throws Exception
	 */
	public Cart findByUser(User user) throws Exception;

}
