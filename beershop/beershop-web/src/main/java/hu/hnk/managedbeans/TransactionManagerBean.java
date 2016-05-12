package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.BusyConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceeded;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.beershop.service.tools.BuyActionRestrictions;
import hu.hnk.loginservices.SessionManager;
import hu.hnk.tool.FacesMessageTool;

/**
 * A felhasználói tranzakciókat kezelő managed bean, amely a felhasználó
 * kosarában levő dolgokat helyezi új rendelésre.
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
	public static final Logger logger = LoggerFactory.getLogger(TransactionManagerBean.class);

	/**
	 * A kosarat kezelő szolgáltatás.
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
	 * A sessiont kezelő managed bean.
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

	private Double shippingCost;

	private Double moneyAfterPayment;

	private List<CartItem> items;

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		totalCost = countTotalCost();
		items = cartService.findByUser(sessionManager.getLoggedInUser())
				.getItems()
				.stream()
				.filter(p -> p.getActive())
				.collect(Collectors.toList());
		shippingCost = BuyActionRestrictions.getShippingCost();

	}

	/**
	 * @return
	 */
	public Double countTotalCost() {
		Double totalCost = cartService.countTotalCost(cartService.findByUser(sessionManager.getLoggedInUser())
				.getItems());
		logger.info("Total cost:" + totalCost);
		return totalCost;
	}

	/**
	 * @return
	 */
	public boolean isThereEnoughMoney() {
		return cargoService.isThereEnoughMoney(totalCost, sessionManager.getLoggedInUser());
	}

	/**
	 * 
	 */
	public void doTransaction() {
		if (isThereEnoughMoney()) {
			Cargo cargo = createNewCargo();
			try {
				cargoService.saveNewCargo(cargo, items);
				FacesMessageTool.createInfoMessage("Sikeres vásárlás.");
			} catch (DailyBuyActionLimitExceeded e) {
				logger.error(e.getMessage(), e);
				FacesMessageTool.createErrorMessage("Sajnálom de túllépte a napi vásárlási limitet.");
			} catch (CanNotBuyLegendaryBeerYetException e) {
				logger.error(e.getMessage(), e);
				FacesMessageTool.createErrorMessage("Nem vásárolhat még legendás terméket.");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				FacesMessageTool.createWarnMessage("Hiba történt a fizetés közben.");
			}

		} else {
			FacesMessageTool.createWarnMessage("Nem áll rendelkezésére elegendő pénz vagy pont.");

		}
	}

	private Cargo createNewCargo() {
		Cargo cargo = new Cargo();
		cargo.setAddress(address);
		cargo.setOrderDate(new Date());
		cargo.setUser(sessionManager.getLoggedInUser());
		cargo.setTotalPrice(totalCost);
		cargo.setPaymentMode(payMode);
		return cargo;
	}

	public Double countMoneyAfterPayment() {
		return cargoService.countMoneyAfterPayment(totalCost, sessionManager.getLoggedInUser());
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

	public Double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

}
