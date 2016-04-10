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

import hu.hnk.beershop.exception.EmailAlreadyTaken;
import hu.hnk.beershop.exception.UsernameNotFound;
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
public class UserServiceImpl implements UserService {

	@EJB
	UserDao userDao;
	@EJB
	RoleDao roleDao;

	/**
	 * A felhasználó mentése.
	 * 
	 * @param user
	 *            A mentendõ felhasználó.
	 */
	public void save(User user) {

		System.out.println("UserService before save!");

		Role role = roleDao.findByName("ROLE_USER");

		if (role == null) {
			System.out.println("Role null->ROLE_USER");
			role.setName("ROLE_USER");
			role = roleDao.save(role);
		}

		User userData = userDao.save(user);
		List<Role> userRoles = userData.getRoles();

		if (userRoles == null || userRoles.isEmpty()) {
			userRoles = new ArrayList<>();
		}

		userRoles.add(role);
		userData.setRoles(userRoles);

		userDao.save(userData);
		System.out.println("UserService after save!");
	}

	/**
	 * Ellenõrzi hogy a regisztrálandó felhasználó születési dátuma alapján
	 * elmúlt-e már 18 éves.
	 */
	public boolean isOlderThanEighteen(Date dateOfBirth) {
		LocalDate now = LocalDate.now();
		Instant instant = Instant.ofEpochMilli(dateOfBirth.getTime());
		LocalDate birth = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return birth.until(now).getYears() > 17;
	}

	@Override
	public User findByUsername(String username) {
		User user = null;
		try {
			user = userDao.findByUsername(username);
		} catch (UsernameNotFound e) {
			
		}
		return user;
	}

	@Override
	public boolean isUsernameAlreadyTaken(String username){
		try{
			User user = userDao.findByUsername(username);
			return true;
		} catch(UsernameNotFound e) {
			return false;
		}
	}

	@Override
	public boolean isEmailAlreadyTaken(String email){
		return userDao.findByEmail(email) != null?true:false;
	}

}
