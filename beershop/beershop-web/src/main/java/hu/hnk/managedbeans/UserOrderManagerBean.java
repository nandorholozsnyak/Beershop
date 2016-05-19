package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.loginservices.SessionManager;

/**
 * @author Nandi
 *
 */
@ManagedBean(name = "userOrderManager")
@ViewScoped
public class UserOrderManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(UserOrderManagerBean.class);

	/**
	 * A munkamenetet kezelő szolgáltatás.
	 */
	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	/**
	 * A szállításokat kezelő szolgáltatás.
	 */
	@EJB
	private CargoService cargoService;

	/**
	 * A felhasználó által leadott rendelések listája.
	 */
	private List<Cargo> userCargos;

	/**
	 * Inicializáló metódus, a szolgáltatás létrejöttekor fut le.
	 */
	@PostConstruct
	public void init() {
		try {
			userCargos = cargoService.findByUser(sessionManager.getLoggedInUser());
		} catch (Exception e) {
			logger.warn("Could not load user orders.");
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Visszaadja a szállítás várható elkészülésének idejét.
	 * 
	 * @param orderDate
	 *            a szállítás leadásának időpontja.
	 * @return a szállítás várható elkészülésének ideje.
	 */
	public String countdownTenMinute(Date orderDate) {
		return cargoService.countdownTenMinutes(orderDate);
	}

	/**
	 * Visszaadja a leadott rendelések listáját.
	 * 
	 * @return a leadott rendelések listája.
	 */
	public List<Cargo> getUserCargos() {
		return userCargos;
	}

	/**
	 * Beállítja a leadott rendelések listáját.
	 * 
	 * @param userCargos
	 *            a leadott rendelések listája.
	 */
	public void setUserCargos(List<Cargo> userCargos) {
		this.userCargos = userCargos;
	}

	/**
	 * Visszaadja a munkamenetet kezelő szolgáltatást.
	 * 
	 * @return a munkamenetet kezelő szolgáltatás.
	 */
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * Beállítja a munkamenetet kezelő szolgáltatást.
	 * 
	 * @param sessionManager
	 *            a munkamenetet kezelő szolgáltatás
	 */
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * Visszaadja a szállításokat kezelő szolgáltatást.
	 * 
	 * @return a szállításokat kezelő szolgáltatás.
	 */
	public CargoService getCargoService() {
		return cargoService;
	}

	/**
	 * Beállítja a szállításokat kezelő szolgáltatást.
	 * 
	 * @param cargoService
	 *            a szállításokat kezelő szolgáltatás.
	 */
	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}

}
