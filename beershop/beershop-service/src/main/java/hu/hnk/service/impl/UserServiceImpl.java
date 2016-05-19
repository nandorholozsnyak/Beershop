package hu.hnk.service.impl;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.DailyMoneyTransferLimitExceededException;
import hu.hnk.beershop.exception.EmailNotFoundException;
import hu.hnk.beershop.exception.InvalidPinCodeException;
import hu.hnk.beershop.exception.UsernameNotFoundException;
import hu.hnk.beershop.model.Rank;
import hu.hnk.beershop.model.Role;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.EventLogService;
import hu.hnk.beershop.service.interfaces.RestrictionCheckerService;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.beershop.service.logfactory.EventLogType;
import hu.hnk.beershop.service.utils.RankInterval;
import hu.hnk.interfaces.RoleDao;
import hu.hnk.interfaces.UserDao;
import hu.hnk.service.cobertura.annotation.CoverageIgnore;
import hu.hnk.service.factory.EventLogFactory;

/**
 * A felhasználói szolgálatásokkal foglalkozó osztály. Enterprise Java Bean.
 * 
 * @author Nandi
 * 
 */
@Stateless(name = "userService")
@Local(UserService.class)
public class UserServiceImpl implements UserService {

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * A felhasználókat kezelő adathozzáférési objektum.
	 */
	@EJB
	private UserDao userDao;

	/**
	 * A jogköröket kezelő adathozzáférési objektum.
	 */
	@EJB
	private RoleDao roleDao;

	/**
	 * Az eseményeket kezelő szolgáltatás.
	 */
	@EJB
	private EventLogService eventLogService;

	/**
	 * A korlátozásokat kezelő szolgáltatás.
	 */
	@EJB
	private RestrictionCheckerService restrictionCheckerService;

	/**
	 * A rangok tapasztalatpontal összekapcsolt objektumot listája.
	 */
	private List<RankInterval> rankIntverals = RankInterval.getRankIntverals();

	/**
	 * A paraméterül kapott felhasználó mentése.
	 * 
	 * @param user
	 *            A mentendő felhasználó.
	 */
	@Override
	@CoverageIgnore
	public void save(User user) throws Exception {
		Role role = roleDao.findByName("ROLE_USER");

		if (role == null) {
			role = new Role();
			role.setName("ROLE_USER");
			try {
				role = roleDao.save(role);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		User userData = null;
		try {
			userData = userDao.save(user);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		List<Role> userRoles = userData.getRoles();

		if (userRoles == null || userRoles.isEmpty()) {
			userRoles = new ArrayList<>();
		}

		userRoles.add(role);
		userData.setRoles(userRoles);
		userData.getCart()
				.setUser(userData);
		try {
			userDao.update(userData);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		eventLogService.save(EventLogFactory.createEventLog(EventLogType.REGISTRATION, userData));
	}

	/**
	 * Ellenőrzi hogy a megadott dátum már "idősebb" mint 18 év.
	 * 
	 * @param dateOfBirth
	 *            a vizsgálandó dátum.
	 * @return igaz ha idősebb, hamis ha még nem.
	 */
	@Override
	public boolean isOlderThanEighteen(Date dateOfBirth) {
		LocalDate now = LocalDate.now();
		Instant instant = Instant.ofEpochMilli(dateOfBirth.getTime());
		LocalDate birth = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
				.toLocalDate();
		return birth.until(now)
				.getYears() > 17;
	}

	/**
	 * Felhasználó keresése a felhasználóneve alapján.
	 * 
	 * @param username
	 *            a keresendő felhasználónév
	 * @return a megtalált felhasználó, ha nincs ilyen akkor null.
	 */
	@Override
	@CoverageIgnore
	public User findByUsername(String username) {
		User user = null;
		try {
			user = userDao.findByUsername(username);
		} catch (UsernameNotFoundException e) {
			logger.warn("Username:" + username + " is not found in our database.");
			logger.error(e.getMessage(), e);
		}
		return user;
	}

	/**
	 * Felhasználónév ellenörzés, a kapott felhasználónevet ellenőrzi hogy
	 * válaszható-e még a regisztráció során.
	 * 
	 * @param username
	 *            az ellenőrizendő felhasználónév.
	 * @return hamis ha szabad a felhasználónév, igaz ha már nem.
	 */
	@Override
	public boolean isUsernameAlreadyTaken(String username) {
		try {
			userDao.findUsername(username);
			return true;
		} catch (UsernameNotFoundException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * E-mail cím ellenörzés, a kapott e-mail címet ellenőrzi hogy válaszható-e
	 * még a regisztráció során.
	 * 
	 * @param email
	 *            az ellenőrizendő e-mail cím.
	 * @return hamis ha szabad a email cím, igaz ha már nem.
	 */
	@Override
	public boolean isEmailAlreadyTaken(String email) {
		try {
			userDao.findEmail(email);
			return true;
		} catch (EmailNotFoundException e) {
			logger.info("E-mail adress is not taken.");
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rank countRankFromXp(User user) {

		if (user.getExperiencePoints() == null) {
			return Rank.AMATUER;
		}
		return rankIntverals.stream()
				.filter(p -> user.getExperiencePoints() > p.getMinimumXP()
						&& user.getExperiencePoints() <= p.getMaximumXP())
				.findFirst()
				.get()
				.getRank();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer countExperiencePointsInPercentage(Double experiencePoints) {

		Integer result = 0;

		if (experiencePoints > -1 && experiencePoints <= 2500) {
			result = (int) ((experiencePoints / 2500) * 100);
		} else if (experiencePoints > 2500 && experiencePoints <= 5000) {
			experiencePoints -= 2500;
			result = (int) ((experiencePoints / 2500) * 100);
		} else if (experiencePoints > 5000) {
			result = 100;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void transferMoney(String userPin, String expectedPin, Integer money, User loggedInUser) throws Exception {
		if (restrictionCheckerService.checkIfUserCanTransferMoney(loggedInUser)) {
			if (!userPin.equals(expectedPin)) {
				logger.info("User entered invalid PIN code.");
				throw new InvalidPinCodeException("PINs are not the same.");
			}
		} else {
			logger.info("User reached the maximum money tranfer limit today.");
			throw new DailyMoneyTransferLimitExceededException("Maximum limit exceeded.");
		}
		loggedInUser.setMoney(loggedInUser.getMoney() + money);
		userDao.update(loggedInUser);
		eventLogService.save(EventLogFactory.createEventLog(EventLogType.MONEYTRANSFER, loggedInUser));
	}

	/**
	 * Beállítja a felhasználókat kezelő adathozzáférési osztályt.
	 * 
	 * @param userDao
	 *            a felhasználókat kezelő adathozzáférési osztály.
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Beállítja az eseményeket kezelő szolgáltatást.
	 * 
	 * @param eventLogService
	 *            az eseményeket kezelő szolgáltatás.
	 */
	public void setEventLogService(EventLogService eventLogService) {
		this.eventLogService = eventLogService;
	}

	/**
	 * Beállítja a korlátozásokat kezelő szolgáltatást.
	 * 
	 * @param restrictionCheckerService
	 *            a korlátozásokat kezelő szolgáltatás.
	 */
	public void setRestrictionCheckerService(RestrictionCheckerService restrictionCheckerService) {
		this.restrictionCheckerService = restrictionCheckerService;
	}

}
