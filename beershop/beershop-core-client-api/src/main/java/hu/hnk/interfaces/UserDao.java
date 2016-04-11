package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.UsernameNotFound;
import hu.hnk.beershop.model.Role;
import hu.hnk.beershop.model.User;

/**
 * @author Nandi
 *
 */
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
	 * @param username a keresendõ felhasználó felhasználóneve.
	 * @return a megtalált felhasználó
	 * @throws UsernameNotFound ha a keresett felhasználónévvel nem található felhasználó.
	 */
	public User findByUsername(String username) throws UsernameNotFound;

	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * 
	 * @param email a keresendõ felhasználó e-mail címe.
	 * @return a megtalált felhasználó
	 * @throws EmailNotFound ha a keresett e-mail címmel felhasználó nem található.
	 */
	public User findByEmail(String email) throws EmailNotFound;

	/**
	 * Egy adott felhasználó törlése.
	 * 
	 * @param user
	 *            a törlendõ felhasználó
	 */
	public void remove(User user);

	
	/**
	 * Felhasználó keresése felhasználónév alapján.
	 * @param username a keresendõ felhasználónév.
	 * @return a kapott felhasználónév.
	 * @throws UsernameNotFound ha a keresett felhasználónévvel nem létezik felhasználó.
	 */
	public String findUsername(String username) throws UsernameNotFound;
	
	/**
	 * Felhasználó keresése e-mail cím alapján.
	 * @param email a keresendõ e-mail cím.
	 * @return a kapott e-mail cím.
	 * @throws EmailNotFound ha a keresett e-mail címmel nem létezik felhasználó.
	 */
	public String findEmail(String email) throws EmailNotFound;
}
