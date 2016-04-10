package hu.hnk.beershop.service.interfaces;

import java.util.Date;

import hu.hnk.beershop.model.User;

public interface UserService {

	public void save(User user);

	public boolean isOlderThanEighteen(Date dateOfBirth);

	public User findByUsername(String username);
	
	/**
	 * Felhasználónév ellenörzés, a kapott felhasználónevet ellenõrzi hogy válaszható-e még a regisztráció során.
	 * @param username az ellenõrizendõ felhasználónév.
	 * @return igaz ha szabad a felhasználónév, hamis ha már nem.
	 */
	public boolean isUsernameAlreadyTaken(String username);
	
	/**
	 * E-mail cím ellenörzés, a kapott e-mail címet ellenõrzi hogy válaszható-e még a regisztráció során.
	 * @param email az ellenõrizendõ e-mail cím.
	 * @return igaz ha szabad a email cím, hamis ha már nem.
	 */
	public boolean isEmailAlreadyTaken(String email);
	
}
