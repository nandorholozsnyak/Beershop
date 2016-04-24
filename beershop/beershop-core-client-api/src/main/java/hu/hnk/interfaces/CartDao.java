package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Cart;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.model.User;

/**
 * A kosarat kezel� adathozz�f�r�si oszt�ly interf�sze.
 * 
 * @author Nandi
 *
 */
public interface CartDao {

	/**
	 * Egy kos�r inform�ci�it lek�rdez� met�dus.
	 * 
	 * @return a kos�r inform�ci�i.
	 */
	public List<Cart> findAll();

	/**
	 * A kos�r tartalm�nak ment�se adatb�zisba.
	 * 
	 * @param cart
	 *            a mentend� kos�r.
	 * @return a mentett kos�r.
	 */
	public Cart save(Cart cart);

	/**
	 * Kos�r keres�se felhaszn�l� alapj�n.
	 * 
	 * @param user
	 *            a keresend� kos�r birtokosa.
	 * @return a megtal�lt kos�r.
	 */
	public Cart findByUser(User user);

	/**
	 * Term�k logikai t�rl�se a kos�rb�l.
	 * 
	 * @param item
	 *            a t�rlend� term�k.
	 * @throws Exception
	 *             b�rmilyen hib�s adatb�zis m�velet eset�n.
	 */
	public void deleteItemLogically(CartItem item) throws Exception;

	/**
	 * Kos�rban l�v� term�k friss�t�se.
	 * 
	 * @param item
	 *            a friss�tend� term�k.
	 * @return a friss�tett term�k.
	 */
	public CartItem updateItem(CartItem item);

}
