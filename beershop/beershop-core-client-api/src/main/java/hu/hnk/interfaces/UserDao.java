package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.exception.EmailNotFoundException;
import hu.hnk.beershop.exception.UsernameNotFoundException;
import hu.hnk.beershop.model.User;

/**
 * A felhasználókat kezelő adathozzáférési osztály implementációja.
 * 
 * {@link User} entitás típusokat kezeli.
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
	 * @throws UsernameNotFoundException
	 *             ha a keresett felhasználónévvel nem található felhasználó.
	 */
	public User findByUsername(String username) throws UsernameNotFoundException;

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendő felhasználó e-mail címe.
	 * @return a megtalált felhasználó
	 * @throws EmailNotFoundException
	 *             ha a keresett e-mail címmel felhasználó nem található.
	 */
	public User findByEmail(String email) throws EmailNotFoundException;

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
	 * @throws UsernameNotFoundException
	 *             ha a keresett felhasználónévvel nem létezik felhasználó.
	 */
	public String findUsername(String username) throws UsernameNotFoundException;

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email
	 *            a keresendő e-mail cím.
	 * @return a kapott e-mail cím.
	 * @throws EmailNotFoundException
	 *             ha a keresett e-mail címmel nem létezik felhasználó.
	 */
	public String findEmail(String email) throws EmailNotFoundException;

	/**
	 * Visszaadja az adatbázisban szereplő összes felhasználót.
	 * 
	 * @return az adatbázisban szereplő felhasználók listája.
	 */
	public List<User> findAll();
}
