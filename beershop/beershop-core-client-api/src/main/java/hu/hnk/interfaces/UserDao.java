package hu.hnk.interfaces;

import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.UsernameNotFound;
import hu.hnk.beershop.model.User;


/**
 * A felhasználókat kezelő adathozzáférési osztály interfésze.
 * 
 * @author Nandi
 *
 */
public interface UserDao extends BaseDao<User> {
	
	/**
	 * Felhasználó keresése felhasználónév alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználó felhasználóneve.
	 * @return a megtalált felhasználó
	 * @throws UsernameNotFound
	 *             ha a keresett felhasználónévvel nem található felhasználó.
	 */
	public User findByUsername(String username) throws UsernameNotFound;

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendő felhasználó e-mail címe.
	 * @return a megtalált felhasználó
	 * @throws EmailNotFound
	 *             ha a keresett e-mail címmel felhasználó nem található.
	 */
	public User findByEmail(String email) throws EmailNotFound;

	/**
	 * Egy adott felhasználó törlése.
	 * 
	 * @param user
	 *            a törlendő felhasználó
	 */
	public void remove(User user);

	/**
	 * Felhasználó keresése felhasználónév alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználónév.
	 * @return a kapott felhasználónév.
	 * @throws UsernameNotFound
	 *             ha a keresett felhasználónévvel nem létezik felhasználó.
	 */
	public String findUsername(String username) throws UsernameNotFound;

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendő e-mail cím.
	 * @return a kapott e-mail cím.
	 * @throws EmailNotFound
	 *             ha a keresett e-mail címmel nem létezik felhasználó.
	 */
	public String findEmail(String email) throws EmailNotFound;
}
