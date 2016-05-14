package hu.hnk.managedbeans;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	 * A sessiont kezelő managed bean.
	 */
	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	@EJB
	private CargoService cargoService;

	private List<Cargo> userCargos;

	@PostConstruct
	public void init() {
		userCargos = cargoService.findByUser(sessionManager.getLoggedInUser());
	}

	public String countdownTenMinute(Date orderDate) {
		return cargoService.countdownTenMinutes(orderDate);
	}

	public List<Cargo> getUserCargos() {
		return userCargos;
	}

	public void setUserCargos(List<Cargo> userCargos) {
		this.userCargos = userCargos;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}

}
