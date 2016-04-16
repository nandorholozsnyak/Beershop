package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import hu.hnk.beershop.model.CartItem;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.loginservices.SessionManager;

/**
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
	public static final Logger logger = Logger.getLogger(CartManagerBean.class);

	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	@EJB
	private CartService cartService;

	private List<CartItem> items;

	@PostConstruct
	public void init() {
		setItems(cartService.findByUser(sessionManager.getLoggedInUser()).getItems());
		logger.info(items);
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
	 * @return the cartService
	 */
	public CartService getCartService() {
		return cartService;
	}

	/**
	 * @param cartService
	 *            the cartService to set
	 */
	public void setCartService(CartService cartService) {
		this.cartService = cartService;
	}

	/**
	 * @return the items
	 */
	public List<CartItem> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<CartItem> items) {
		this.items = items;
	}

}
