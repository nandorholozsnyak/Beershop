package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.DailyMoneyTransferLimitExceeded;
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
	public static final Logger logger = LoggerFactory.getLogger(MoneyTransferManagerBean.class);

	@EJB
	private UserService userService;

	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	private Map<Integer, Integer> moneyValues;
	private String pin;
	private String userPin;
	private String money;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 * 
	 * Meghívja a beállítandó pénz mennyiségeket létrehozo metódust.
	 */
	@PostConstruct
	public void init() {
		generateMoneyTransferFields();

	}

	private void generateMoneyTransferFields() {
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

	/**
	 * Az egyenlegfeltöltést elvégző metódus, amely meghívja a szolgáltatások
	 * megfelelő metódusait.
	 */
	public void transferMoney() {

		try {
			userService.transferMoney(userPin, pin, Integer.valueOf(money), sessionManager.getLoggedInUser());
			FacesMessageTool.createInfoMessage("A pénz feltöltése megtörtént.");
			generateMoneyTransferFields();
		} catch (NumberFormatException e) {
			logger.warn(e.getMessage(), e);
			FacesMessageTool.createWarnMessage("Az ellenörző mezőbe csak számot írjon!");
		} catch (InvalidPinCode e) {
			logger.warn(e.getMessage(), e);
			FacesMessageTool.createWarnMessage("Az ellenörző pin kód nem egyezik meg.");
		} catch (DailyMoneyTransferLimitExceeded e) {
			logger.warn(e.getMessage(), e);
			FacesMessageTool.createErrorMessage("Túllépte a napi limitet.");
		}

	}

	/**
	 * Visszaadja a megfelelő pénz - érték párokat.
	 * 
	 * @return pénz-érték párok.
	 */
	public Map<Integer, Integer> getMoneyValues() {
		return moneyValues;
	}

	/**
	 * Beállítja a megfelelő pénz - érték párokat.
	 * 
	 * @param moneyValues
	 *            pénz - érték párok.
	 * 
	 */
	public void setMoneyValues(Map<Integer, Integer> moneyValues) {
		this.moneyValues = moneyValues;
	}

	/**
	 * Visszaadja a session-t kezelő objektumot.
	 * 
	 * @return a sessiont kezelő objektum.
	 */
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * Beállítja a session-t kezelő objektumot.
	 * 
	 * @param sessionManager
	 *            a session-t kezelő objetum.
	 * 
	 */
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * Visszaadja a PIN kódot.
	 * 
	 * @return a PIN kód.
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * Beállítja a PIN kódot.
	 * 
	 * @param pin
	 *            a beállítandó PIN kód.
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * Visszaadja a felhasználó által begépelt PIN kódot.
	 * 
	 * @return a felhasználó által begépelt PIN kód.
	 */
	public String getUserPin() {
		return userPin;
	}

	/**
	 * Beállítja a felhasználó által begépelt PIN kódot.
	 * 
	 * @param userPin
	 *            a felhasználó által begépelt PIN kód.
	 */
	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	/**
	 * Visszaadja a választott pénz mennyiséget.
	 * 
	 * @return a válaszott pénz mennyiség
	 */
	public String getMoney() {
		return money;
	}

	/**
	 * Beállítja a választandó pénz mennyiséget.
	 * 
	 * @param money
	 *            a választott pénz mennyiség.
	 */
	public void setMoney(String money) {
		this.money = money;
	}

}
