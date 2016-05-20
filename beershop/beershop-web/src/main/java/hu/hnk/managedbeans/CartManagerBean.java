package hu.hnk.managedbeans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.loginservices.SessionManager;
import hu.hnk.tool.FacesMessageTool;

/**
 * A kosarat kezelő bean amellyel a UI felületen tudunk dolgozni.
 * 
 * @author Nandi
 *
 */
@ManagedBean(name = "cartManagerBean")
@ViewScoped
public class CartManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = LoggerFactory.getLogger(CartManagerBean.class);

	/**
	 * A munkamenetet kezelő szolgáltatás.
	 */
	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	/**
	 * A kosarat kezelő szolgáltatás.
	 */
	@EJB
	private CartService cartService;

	/**
	 * A felhasználó kosarában lévő termékek listája.
	 */
	private List<CartItem> items;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
	@PostConstruct
	public void init() {
		loadUserItems();
	}

	/**
	 * A felhasználó kosarának tartalmát betöltő metódus.
	 */
	private void loadUserItems() {
		logger.info("cartManagerBean init");
		try {
			items = cartService.findByUser(sessionManager.getLoggedInUser())
					.getItems()
					.stream()
					.filter(p -> p.getActive())
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.warn("Could not load user items.");
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Visszadja formázottan a teljes összeget, amelyet majd fizetni kell.
	 * 
	 * @return a fizetendő összeg.
	 */
	public String getTotalPrice() {
		NumberFormat format = new DecimalFormat("#0.00");
		return format.format(cartService.countTotalCost(items));
	}

	/**
	 * A paraméterül kapott terméket törli a kosárból.
	 * 
	 * @param item
	 *            a törlendő termék.
	 */
	public void deleteItemFromCart(CartItem item) {

		try {
			cartService.deletItemFromCart(item);
			FacesMessageTool.createInfoMessage("Módosítások sikeresen mentve!");
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			FacesMessageTool.createWarnMessage("Módosításokat nem tudtuk menteni!");
		}
		loadUserItems();
		refreshXhtmlComponents();

	}

	/**
	 * Törlés utáni XHTML komponensek frissítésének metódusa.
	 */
	private void refreshXhtmlComponents() {
		FacesContext.getCurrentInstance()
				.getPartialViewContext()
				.getRenderIds()
				.add("cart:cartTable");
		FacesContext.getCurrentInstance()
				.getPartialViewContext()
				.getRenderIds()
				.add("cart:cartMessage");
		FacesContext.getCurrentInstance()
				.getPartialViewContext()
				.getRenderIds()
				.add("cart:emptyCart");
	}

	/**
	 * Visszaadja formázottan a bónusz pontokat.
	 * 
	 * @return a bónusz pontok formázottan.
	 */
	public String countBonusPoints() {
		NumberFormat format = new DecimalFormat("#0.00");
		return format.format(cartService.countBonusPoints(items));
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
	 * Visszaadja a kosárban lévő termékek listáját.
	 * 
	 * @return a korásban lévő termékek listája.
	 */
	public List<CartItem> getItems() {
		return items;
	}

	/**
	 * Beállítja a kosárban lévő termékek listáját.
	 * 
	 * @param items
	 *            a kosárba teendő termékek listája.
	 */
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

}
