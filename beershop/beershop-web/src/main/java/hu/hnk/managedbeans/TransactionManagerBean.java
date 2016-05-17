package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.CanNotBuyLegendaryBeerYetExceptionException;
import hu.hnk.beershop.exception.DailyBuyActionLimitExceededException;
import hu.hnk.beershop.model.Cargo;
import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.service.interfaces.CargoService;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.beershop.service.restrictions.BuyActionRestrictions;
import hu.hnk.beershop.service.utils.PaymentMode;
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
	 * A szállításokat kezelő szolgáltatás.
	 */
	@EJB
	private CargoService cargoService;

	/**
	 * A munkamenetet kezelő szolgáltatás.
	 */
	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	/**
	 * A szállítási hely címe.
	 */
	private String address;

	/**
	 * A szállítás tervezett fizetési eszköze.
	 */
	private PaymentMode paymentMode;

	/**
	 * A szállításban résztvevő termékek szummázott összege.
	 */
	private Double totalCost;

	/**
	 * A szállítási költség.
	 */
	private Double shippingCost;

	/**
	 * A felhasználónak jelzett fizetés utáni összege.
	 */
	private Double moneyAfterPayment;

	/**
	 * A felhasználó kosárban, illetve most a szállítandó termékek listája.
	 */
	private List<CartItem> items;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
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
		paymentMode = PaymentMode.MONEY;

	}

	/**
	 * Kiszámolja a fizetendő összeget, amelyet megjelenítünk a felhasználónak.
	 * 
	 * @return a vásárlás összege, szállítási díj nélkül.
	 */
	public Double countTotalCost() {
		Double totalCost = cartService.countTotalCost(cartService.findByUser(sessionManager.getLoggedInUser())
				.getItems());
		logger.info("Total cost:" + totalCost);
		return totalCost;
	}

	/**
	 * Ellenőrzi hogy a felhasználó rendelkezik-e elég pénzzel.
	 * 
	 * @return <code>true</code> ha van elegendő pénze/bónusz pontja, egyébként
	 *         <code>false</code>
	 */
	public boolean isThereEnoughMoney() {
		return cargoService.isThereEnoughMoney(totalCost, sessionManager.getLoggedInUser(), paymentMode);
	}

	/**
	 * A tranzakciót előkészítő metódus.
	 * 
	 * A megadott adatokból létrehozza az új szállítás entitást amit utána
	 * továbbküld a szállításokat kezelő szolgáltatásnak.
	 */
	public void doTransaction() {
		logger.info("PaymentMode: {}", paymentMode);
		if (isThereEnoughMoney()) {
			Cargo cargo = createNewCargo();
			try {
				cargoService.saveNewCargo(cargo, items);
				FacesMessageTool.createInfoMessage("Sikeres vásárlás.");
			} catch (DailyBuyActionLimitExceededException e) {
				logger.error(e.getMessage(), e);
				FacesMessageTool.createErrorMessage("Sajnálom de túllépte a napi vásárlási limitet.");
			} catch (CanNotBuyLegendaryBeerYetExceptionException e) {
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

	/**
	 * A megadott adatok alapján létrehoz egy új szállítás entitást.
	 * 
	 * @return a megadott adatok alapján létrehozott új entitás.
	 */
	private Cargo createNewCargo() {
		Cargo cargo = new Cargo();
		cargo.setAddress(address);
		cargo.setOrderDate(new Date());
		cargo.setUser(sessionManager.getLoggedInUser());
		cargo.setTotalPrice(totalCost);
		cargo.setPaymentMode(paymentMode.getValue());
		return cargo;
	}

	/**
	 * Visszaadja a felhasznlói felületre a vásárlás utáni összeget, amivel a
	 * még a felhasználó fog rendelkezni.
	 * 
	 * @return a vásárlás utáni egyenleg státusza.
	 */
	public Double countMoneyAfterPayment() {
		return cargoService.countMoneyAfterPayment(totalCost, sessionManager.getLoggedInUser(), paymentMode);
	}

	/**
	 * Visszaadja a felhasználó által megadott szállítási címet.
	 * 
	 * @return a felhasználó által megadott szállítási cím.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Beállítja a felhasználó által begépelt szállítási címet.
	 * 
	 * @param addres
	 *            a felhasználó által begépelt szállítási cím.
	 */
	public void setAddress(String addres) {
		this.address = addres;
	}

	/**
	 * Visszaadja a fizetés típusát.
	 * 
	 * @return a fizetés típusa.
	 */
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	/**
	 * Visszaadja a fizetés típusát.
	 * 
	 * @param paymentMode
	 *            a fizetés típusa.
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * Visszaadja a kosarat kezelő szolgáltatást.
	 * 
	 * @return a kosarat kezelő szolgáltatás.
	 */
	public CartService getCartService() {
		return cartService;
	}

	/**
	 * Beállítja a kosarat kezelő szolgáltatást.
	 * 
	 * @param cartService
	 *            a kosarat kezelő szolgáltatás.
	 */
	public void setCartService(CartService cartService) {
		this.cartService = cartService;
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
	 * Visszaadja a fizetendő összeget.
	 * 
	 * @return a fizetendő összeg.
	 */
	public Double getTotalCost() {
		return totalCost;
	}

	/**
	 * Beállítja a fizetendő összeget.
	 * 
	 * @param totalCost
	 *            a fizetendő összeg.
	 */
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
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

	/**
	 * Visszaadja a felhasználó számára a fizetési utáni egyenlegét.
	 * 
	 * @return a fizetés utáni felhasználói egyenleg.
	 */
	public Double getMoneyAfterPayment() {
		return moneyAfterPayment;
	}

	/**
	 * Beállítja a fizetés utáni egyenleget.
	 * 
	 * @param moneyAfterPayment
	 *            a fizetés utáni egyenleg.
	 */
	public void setMoneyAfterPayment(Double moneyAfterPayment) {
		this.moneyAfterPayment = moneyAfterPayment;
	}

	/**
	 * Visszaadja a szállítási költséget.
	 * 
	 * @return szállítási költség.
	 */
	public Double getShippingCost() {
		return shippingCost;
	}

	/**
	 * Beállítja a szállítási költséget.
	 * 
	 * @param shippingCost
	 *            a szállítási költség.
	 */
	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

}
