package hu.hnk.interfaces;

import hu.hnk.beershop.model.User;

public interface UserDao {
	/**
	 * Új felhasználó mentése.
	 * 
	 * @param user
	 *            az új felhasználó
	 * @return a mentett felhasználó
	 */
	public User save(User user);

	/**
	 * Felhasználó keresése felhasználónév alapján.
	 * 
	 * @param username
	 *            a keresendõ felhasználó felhasználóneve.
	 * @return a megtalált felhasználó
	 */
	public User findByUsername(String username);

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendõ felhasználó e-mail címe.
	 * @return a megtalált felhasználó
	 */
	public User findByEmail(String email);
	/**
	 * Egy adott felhasználó törlése.
	 * @param user a törlendõ felhasználó
	 */
	public void remove(User user);
}
