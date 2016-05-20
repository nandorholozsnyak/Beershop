package hu.hnk.loginservices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.hnk.beershop.model.Role;
import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;

/**
 * A Springes bejelentkeztetést végző szolgáltatás.
 * 
 * Segítségével tudjuk azonosítani a felhasználókat amint bejelentkeznek.
 * 
 * @author Nandi
 *
 */
@Service("loginManager")
@EJB(name = "hu.hnk.beershop.UserService", beanInterface = UserService.class)
public class LoginService implements Serializable, UserDetailsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856400278569714670L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	/**
	 * A felhasználó szolgáltatások kezelője.
	 */
	@EJB
	private UserService userService;

	/**
	 * A bejelentkező felhasználó megadott felhasználóneve.
	 */
	private String username;

	/**
	 * A bejelentkező felhasználó megadott jelszava.
	 */
	private String password;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Felhsználó betöltése felhasználónév alapján.
	 * 
	 * @param username
	 *            a betöltendő felhasználó felhasználóneve.
	 * @return a betöltött felhasználó.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;

		try {
			user = userService.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException(username);
			}
			List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
					true, true, true, authorities);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UsernameNotFoundException(e.getMessage());
		}

	}

	/**
	 * A felhasználó jogköreit betöltő metódus.
	 * 
	 * @param userRoles
	 *            a felhasználó jogkörei.
	 * @return a betöltött jogkörök.
	 */
	private List<GrantedAuthority> buildUserAuthority(List<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<>();

		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		return new ArrayList<>(setAuths);

	}

}
