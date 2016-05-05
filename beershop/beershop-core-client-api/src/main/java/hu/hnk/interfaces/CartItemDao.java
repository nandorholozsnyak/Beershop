package hu.hnk.interfaces;

import hu.hnk.beershop.model.CartItem;


/**
 * @author Nandi
 *
 */
public interface CartItemDao extends BaseDao<CartItem> {

	/**
	 * Termék logikai törlése a kosárból.
	 * 
	 * @param item
	 *            a törlendő termék.
	 * @throws Exception
	 *             bármilyen hibás adatbázis művelet esetén.
	 */
	public void deleteItemLogically(CartItem item) throws Exception;
}
