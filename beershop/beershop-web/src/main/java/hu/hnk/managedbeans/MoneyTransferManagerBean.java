package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import hu.hnk.beershop.exception.InvalidPinCode;
import hu.hnk.beershop.service.interfaces.UserService;
import hu.hnk.loginservices.SessionManager;
import hu.hnk.tool.FacesMessageTool;

/**
 * @author Nandi
 *
 */
@ManagedBean(name = "moneyTransferManagerBean")
@ViewScoped
public class MoneyTransferManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(MoneyTransferManagerBean.class);

	@EJB
	UserService userService;

	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	private Map<Integer, Integer> moneyValues;
	private String pin;
	private String userPin;
	private String money;
	private FacesMessage msg;

	@PostConstruct
	public void init() {
		moneyValues = new LinkedHashMap<>();
		getMoneyValues().put(1000, 1000);
		getMoneyValues().put(2000, 2000);
		getMoneyValues().put(2500, 2500);
		getMoneyValues().put(5000, 5000);
		getMoneyValues().put(10000, 10000);
		getMoneyValues().put(20000, 20000);
		getMoneyValues().put(50000, 50000);
		logger.info("Money values set.");
		setPin(String.valueOf((int) (Math.random() * 9000) + 1000));
		logger.info("Pin code made.");

	}

	public void transferMoney() {
		try {
			userService.transferMoney(userPin, pin, Integer.valueOf(money), sessionManager.getLoggedInUser());
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "A pénz feltöltése megtörtént.",
					"A pénz feltöltése megtörtént.");
		} catch (NumberFormatException e) {
			logger.warn(e);
		} catch (InvalidPinCode e) {
			logger.warn(e);
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Az ellenörzõ pin kód nem egyezik meg.",
					"Az ellenörzõ pin kód nem egyezik meg.");
		}
		
		FacesMessageTool.publishMessage(msg);
	}

	/**
	 * @return the moneyValues
	 */
	public Map<Integer, Integer> getMoneyValues() {
		return moneyValues;
	}

	/**
	 * @param moneyValues
	 *            the moneyValues to set
	 */
	public void setMoneyValues(Map<Integer, Integer> moneyValues) {
		this.moneyValues = moneyValues;
	}

	/**
	 * @return the sessionManager
	 */
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * @param sessionManager
	 *            the sessionManager to set
	 */
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin
	 *            the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return the userPin
	 */
	public String getUserPin() {
		return userPin;
	}

	/**
	 * @param userPin
	 *            the userPin to set
	 */
	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	/**
	 * @return the money
	 */
	public String getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(String money) {
		this.money = money;
	}

}
