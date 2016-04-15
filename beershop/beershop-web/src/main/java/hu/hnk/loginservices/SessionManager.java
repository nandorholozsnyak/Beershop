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

import hu.hnk.beershop.model.User;
import hu.hnk.beershop.service.interfaces.UserService;

/**
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
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			if (getLoggedInUser() == null) {
				setLoggedInUser(null);
				Principal principal = req.getUserPrincipal();
				if (principal != null) {
					String userName = principal.getName();
					try {
						setLoggedInUser(userService.findByUsername(userName));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {

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

	public String getUserRank() {
		return userService.countRankFromXp(loggedInUser).toString();
	}

}
