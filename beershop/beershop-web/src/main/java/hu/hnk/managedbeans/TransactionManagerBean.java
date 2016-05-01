package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.loginservices.SessionManager;
import hu.hnk.tool.FacesMessageTool;

/**
 * A felhasználói tranzakciókat kezelõ managed bean, amely a felhasználó
 * kosarában levõ dolgokat helyezi új rendelésre.
 * 
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
	 * 
	 */
	@EJB
	private CargoService cargoService;

	private FacesMessage msg;

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

	private Double moneyAfterPayment;

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		totalCost = countTotalCost();
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
		return cargoService.isThereEnoughMoney(sessionManager.getLoggedInUser());
	}

	/**
	 * 
	 */
	public void doTransaction() {
		if (isThereEnoughMoney()) {
			Cargo cargo = new Cargo();
			cargo.setItems(sessionManager.getLoggedInUser()
					.getCart()
					.getItems()
					.stream()
					.filter(p -> p.getActive())
					.collect(Collectors.toList()));
			cargo.setAddress(address);
			cargo.setOrderDate(new Date());
			cargo.setUser(sessionManager.getLoggedInUser());
			cargo.setTotalPrice(totalCost);
			cargo.setPaymentMode(payMode);
			try {
				cargoService.saveNewCargo(cargo);
				FacesMessageTool.createInfoMessage("Sikeres vásárlás.");
			} catch (Exception e) {
				FacesMessageTool.createWarnMessage("Hiba történt a fizetés közben.");
			}

		} else {
			FacesMessageTool.createWarnMessage("Nem áll rendelkezésére elegendõ pénz vagy pont.");

		}
	}

	public void countMoneyAfterPayment() {

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

	public CargoService getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	public FacesMessage getMsg() {
		return msg;
	}

	public void setMsg(FacesMessage msg) {
		this.msg = msg;
	}

	public Double getMoneyAfterPayment() {
		return moneyAfterPayment;
	}

	public void setMoneyAfterPayment(Double moneyAfterPayment) {
		this.moneyAfterPayment = moneyAfterPayment;
	}

}
