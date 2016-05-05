package hu.hnk.beershop.service.interfaces;

import java.util.Date;

import hu.hnk.beershop.exception.InvalidPinCode;
import hu.hnk.beershop.exception.DailyMoneyTransferLimitExceeded;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.User;


/**
 * A felhasználó entitáshoz kapcsolódó szolgáltatások interfésze.
 * 
 * @author Nandi
 *
 */
public interface UserService {

	/**
	 * Felhasználó mentése.
	 * 
	 * @param user
	 *            a mentendő felhasználó.
	 */
	public void save(User user);

	/**
	 * Ellenőrzi hogy a megadott dátum már "idősebb" mint 18 év.
	 * 
	 * @param dateOfBirth
	 *            a vizsgálandó dátum.
	 * @return igaz ha idősebb, hamis ha még nem.
	 */
	public boolean isOlderThanEighteen(Date dateOfBirth);

	/**
	 * Felhasználó keresése a felhasználóneve alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználónév
	 * @return a megtalált felhasználó, ha nincs ilyen akkor null.
	 */
	public User findByUsername(String username);

	/**
	 * Felhasználónév ellenörzés, a kapott felhasználónevet ellenőrzi hogy
	 * válaszható-e még a regisztráció során.
	 * 
	 * @param username
	 *            az ellenőrizendő felhasználónév.
	 * @return igaz ha szabad a felhasználónév, hamis ha már nem.
	 */
	public boolean isUsernameAlreadyTaken(String username);

	/**
	 * E-mail cím ellenörzés, a kapott e-mail címet ellenőrzi hogy válaszható-e
	 * még a regisztráció során.
	 * 
	 * @param email
	 *            az ellenőrizendő e-mail cím.
	 * @return igaz ha szabad a email cím, hamis ha már nem.
	 */
	public boolean isEmailAlreadyTaken(String email);

	/**
	 * A felhasználó rangjának való számítása. A számítást segítő függvény a
	 * RankInterval nevű osztályt használja.
	 * 
	 * @param user
	 * @return
	 */
	public Rank countRankFromXp(User user);

	public Integer countExperiencePointsInPercentage(Double experiencePoints);

	public void transferMoney(String userPin, String expectedPin, Integer money, User loggedInUser)
			throws InvalidPinCode, DailyMoneyTransferLimitExceeded;
}
