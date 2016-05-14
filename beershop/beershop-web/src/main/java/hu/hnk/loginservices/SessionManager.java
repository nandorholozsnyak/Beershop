/**
 * 
 */
package hu.hnk.loginservices;

import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;

/**
 * A session-t kezelő bean, amely a felhasználó bejelentkezés utáni adatait
 * tartja számon.
 * 
 * @author Nandi
 *
 */
@ManagedBean(name = "sessionManagerBean")
@SessionScoped
public class SessionManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7564722048228872937L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

	/**
	 * A felhasználó szolgáltatás.
	 */
	@EJB
	private UserService userService;

	/**
	 * A bejelentkezett felhasználó tárolt adatai.
	 */
	private User loggedInUser;

	/**
	 * Inicializáló metódus, ami a managed bean létrehozásakor lefut.
	 */
	@PostConstruct
	public void init() {

		try {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext()
					.getRequest();
			if (getLoggedInUser() == null) {
				setLoggedInUser(null);
				Principal principal = req.getUserPrincipal();
				if (principal != null) {
					String userName = principal.getName();
					setLoggedInUserAfterLogin(userName);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void setLoggedInUserAfterLogin(String userName) {
		try {
			setLoggedInUser(userService.findByUsername(userName));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Bejelentkezett felhasználó lekérése.
	 * 
	 * @return a bejelentkezett felhasználó.
	 */
	public User getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * A bejelentkezett felhasználó beállítása.
	 * 
	 * @param loggedInUser
	 *            a bejelentkezett felhasználó.
	 */
	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	/**
	 * Visszaadja a bejelentkezett felhasználó rangját.
	 * 
	 * @return a bejelentkezett felhasználó rangja.
	 */
	public String getUserRank() {
		return userService.countRankFromXp(loggedInUser)
				.getRankName();
	}

	/**
	 * Visszaadja a bejelentkezett felhasználó tapasztalat pontjait.
	 * 
	 * @return a bejelentkezett felhasználó tapasztalat pontjai.
	 */
	public Integer getUserExperiencePoints() {
		return userService.countExperiencePointsInPercentage(loggedInUser.getExperiencePoints());
	}

}
