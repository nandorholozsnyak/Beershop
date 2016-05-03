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
	 *            a mentendõ felhasználó.
	 */
	public void save(User user);

	/**
	 * Ellenõrzi hogy a megadott dátum már "idõsebb" mint 18 év.
	 * 
	 * @param dateOfBirth
	 *            a vizsgálandó dátum.
	 * @return igaz ha idõsebb, hamis ha még nem.
	 */
	public boolean isOlderThanEighteen(Date dateOfBirth);

	/**
	 * Felhasználó keresése a felhasználóneve alapján.
	 * 
	 * @param username
	 *            a keresendõ felhasználónév
	 * @return a megtalált felhasználó, ha nincs ilyen akkor null.
	 */
	public User findByUsername(String username);

	/**
	 * Felhasználónév ellenörzés, a kapott felhasználónevet ellenõrzi hogy
	 * válaszható-e még a regisztráció során.
	 * 
	 * @param username
	 *            az ellenõrizendõ felhasználónév.
	 * @return igaz ha szabad a felhasználónév, hamis ha már nem.
	 */
	public boolean isUsernameAlreadyTaken(String username);

	/**
	 * E-mail cím ellenörzés, a kapott e-mail címet ellenõrzi hogy válaszható-e
	 * még a regisztráció során.
	 * 
	 * @param email
	 *            az ellenõrizendõ e-mail cím.
	 * @return igaz ha szabad a email cím, hamis ha már nem.
	 */
	public boolean isEmailAlreadyTaken(String email);

	public Rank countRankFromXp(User user);

	public Integer countExperiencePointsInPercentage(Double experiencePoints);

	public void transferMoney(String userPin, String expectedPin, Integer money, User loggedInUser)
			throws InvalidPinCode, DailyMoneyTransferLimitExceeded;
}
