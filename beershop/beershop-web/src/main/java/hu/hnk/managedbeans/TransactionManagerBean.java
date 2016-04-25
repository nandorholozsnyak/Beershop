package hu.hnk.managedbeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.loginservices.SessionManager;

/**
 * @author Nandi
 *
 */
@ManagedBean(name = "transactionManagerBean")
@ViewScoped
public class TransactionManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(TransactionManagerBean.class);

	/**
	 * A kosarat kezelõ szolgáltatás.
	 */
	@EJB
	private CartService cartService;

	/**
	 * A sessiont kezelõ managed bean.
	 */
	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	/**
	 * A vásárló címe, ahová a termékeket szállítjuk majd.
	 */
	private String address;

	/**
	 * Fizetési mód, utalással vagy bónuszpontokkal.
	 */
	private String payMode;

	private Double totalCost;

	@PostConstruct
	public void init() {
		setTotalCost(countTotalCost());
	}

	/**
	 * @return
	 */
	public Double countTotalCost() {
		Double totalCost = cartService.countTotalCost(sessionManager.getLoggedInUser()
				.getCart()
				.getItems());
		logger.info("Total cost:" + totalCost);
		return totalCost;
	}

	/**
	 * @return
	 */
	public boolean isThereEnoughMoney() {
		return totalCost <= sessionManager.getLoggedInUser()
				.getMoney();
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param addres
	 */
	public void setAddress(String addres) {
		this.address = addres;
	}

	/**
	 * @return
	 */
	public String getPayMode() {
		return payMode;
	}

	/**
	 * @param payMode
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public CartService getCartService() {
		return cartService;
	}

	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

}
