package hu.hnk.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import hu.hnk.beershop.exception.EmailNotFound;
import hu.hnk.beershop.exception.InvalidPinCode;
import hu.hnk.beershop.exception.UsernameNotFound;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.Role;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.interfaces.RoleDao;
import hu.hnk.interfaces.UserDao;

/**
 * A felhasználói szolgálatásokkal foglalkozó osztály. Enterprise Java Bean.
 * 
 * @author Nandi
 * 
 */
@Stateless(name = "userService")
@Local(UserService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserServiceImpl implements UserService {

	/**
	 * A felhasználókat kezelõ adathozzáférési objektum.
	 */
	@EJB
	private UserDao userDao;

	/**
	 * A jogköröket kezelõ adathozzáférési objektum.
	 */
	@EJB
	private RoleDao roleDao;

	/**
	 * A felhasználó mentése.
	 * 
	 * @param user
	 *            A mentendõ felhasználó.
	 */
	public void save(User user) {
		Role role = roleDao.findByName("ROLE_USER");

		if (role == null) {
			role = new Role();
			role.setName("ROLE_USER");
			role = roleDao.save(role);
		}

		User userData = getUserDao().save(user);
		List<Role> userRoles = userData.getRoles();

		if (userRoles == null || userRoles.isEmpty()) {
			userRoles = new ArrayList<>();
		}

		userRoles.add(role);
		userData.setRoles(userRoles);
		userData.getCart().setUser(userData);
		getUserDao().save(userData);
	}

	/**
	 * Ellenõrzi hogy a megadott dátum már "idõsebb" mint 18 év.
	 * 
	 * @param dateOfBirth
	 *            a vizsgálandó dátum.
	 * @return igaz ha idõsebb, hamis ha még nem.
	 */
	public boolean isOlderThanEighteen(Date dateOfBirth) {
		LocalDate now = LocalDate.now();
		Instant instant = Instant.ofEpochMilli(dateOfBirth.getTime());
		LocalDate birth = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return birth.until(now).getYears() > 17;
	}

	/**
	 * Felhasználó keresése a felhasználóneve alapján.
	 * 
	 * @param username
	 *            a keresendõ felhasználónév
	 * @return a megtalált felhasználó, ha nincs ilyen akkor null.
	 */
	@Override
	public User findByUsername(String username) {
		User user = null;
		try {
			user = getUserDao().findByUsername(username);
		} catch (UsernameNotFound e) {

		}
		return user;
	}

	/**
	 * Felhasználónév ellenörzés, a kapott felhasználónevet ellenõrzi hogy
	 * válaszható-e még a regisztráció során.
	 * 
	 * @param username
	 *            az ellenõrizendõ felhasználónév.
	 * @return hamis ha szabad a felhasználónév, igaz ha már nem.
	 */
	@Override
	public boolean isUsernameAlreadyTaken(String username) {
		try {
			getUserDao().findUsername(username);
			return true;
		} catch (UsernameNotFound e) {
			return false;
		}
	}

	/**
	 * E-mail cím ellenörzés, a kapott e-mail címet ellenõrzi hogy válaszható-e
	 * még a regisztráció során.
	 * 
	 * @param email
	 *            az ellenõrizendõ e-mail cím.
	 * @return hamis ha szabad a email cím, igaz ha már nem.
	 */
	@Override
	public boolean isEmailAlreadyTaken(String email) {
		try {
			getUserDao().findEmail(email);
			return true;
		} catch (EmailNotFound e) {
			return false;
		}
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Rank countRankFromXp(User user) {
		Rank userRank = null;
		if (user.getExperiencePoints() > -1 && user.getExperiencePoints() <= 2500) {
			userRank = Rank.Amatuer;
		} else if (user.getExperiencePoints() > 2500 && user.getExperiencePoints() <= 7500) {
			userRank = Rank.Beginner;
		} else if (user.getExperiencePoints() > 7500) {
			userRank = Rank.Expert;
		}
		return userRank;
	}

	@Override
	public Integer countExperiencePointsInPercentage(Double experiencePoints) {

		Integer result = 0;

		if (experiencePoints > -1 && experiencePoints <= 2500) {
			result = (int) ((experiencePoints / 2500) * 100);
		} else if (experiencePoints > 2500 && experiencePoints <= 7500) {
			result = (int) ((experiencePoints / 7500) * 100);
		} else if (experiencePoints > 7500) {
			result = 100;
		}

		return result;
	}

	@Override
	public void transferMoney(String userPin, String expectedPin, Integer money, User loggedInUser)
			throws InvalidPinCode {
		if (!userPin.equals(expectedPin)) {
			throw new InvalidPinCode("PINs are not the same.");
		}
		loggedInUser.setMoney(loggedInUser.getMoney() + money);
		userDao.save(loggedInUser);
	}

}
