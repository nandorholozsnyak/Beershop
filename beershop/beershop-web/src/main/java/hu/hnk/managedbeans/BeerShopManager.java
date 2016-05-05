package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import hu.hnk.beershop.exception.NegativeQuantityNumber;
import hu.hnk.beershop.exception.StorageItemQuantityExceeded;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.service.interfaces.BeerService;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.beershop.service.interfaces.StorageService;
import hu.hnk.loginservices.SessionManager;
import hu.hnk.tool.FacesMessageTool;


/**
 * @author Nandi
 *
 */
@ManagedBean(name = "beerShopManager")
@ViewScoped
public class BeerShopManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Az osztály loggere.
	 */
	public static final Logger logger = Logger.getLogger(BeerShopManager.class);

	/**
	 * A söröket kezelő szolgáltatás.
	 */
	@EJB
	private BeerService beerService;

	/**
	 * A raktárt kezelő szolgáltatás.
	 */
	@EJB
	private StorageService storageService;

	/**
	 * A kosarat kezelő szolgáltatás.
	 */
	@EJB
	private CartService cartService;

	@ManagedProperty(value = "#{sessionManagerBean}")
	private SessionManager sessionManager;

	/**
	 * A sörök listája.
	 */
	private List<Beer> beersInShop;

	/**
	 * A kiválasztott sör.
	 */
	private Beer selectedBeer;

	private Map<Beer, Integer> beersToCart;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
	@PostConstruct
	public void init() {
		setBeersInShop(beerService.findAll());
		resetCart();
	}

	private void resetCart() {
		beersToCart = new HashMap<>();
		for (Beer b : beersInShop) {
			beersToCart.put(b, 0);
		}
	}

	public void incrementBeer(Beer beer) {
		Integer quantity = beersToCart.get(beer);
		List<StorageItem> items = storageService.findAll();

		try {
			storageService.checkStorageItemQuantityLimit(items, beer, ++quantity);
			beersToCart.put(beer, quantity);
		} catch (StorageItemQuantityExceeded e) {
			logger.warn(e.getMessage());

			beersToCart.put(beer, items.stream()
					.filter(p -> p.getBeer()
							.equals(beer))
					.findFirst()
					.get()
					.getQuantity());
			FacesMessageTool.createInfoMessage("A raktár maximumát elérte.");
		} catch (NegativeQuantityNumber e) {
			logger.warn(e.getMessage());
		}

	}

	public void decreaseBeer(Beer beer) {

		Integer quantity = beersToCart.get(beer);
		List<StorageItem> items = storageService.findAll();
		try {
			storageService.checkStorageItemQuantityLimit(storageService.findAll(), beer, --quantity);
			beersToCart.put(beer, quantity);
		} catch (StorageItemQuantityExceeded e) {
			logger.warn(e.getMessage());
			beersToCart.put(beer, items.stream()
					.filter(p -> p.getBeer()
							.equals(beer))
					.findFirst()
					.get()
					.getQuantity());
		} catch (NegativeQuantityNumber e) {
			logger.warn(e.getMessage());
			FacesMessageTool.createWarnMessage("A darabszám nem lehet negatív érték!");
		}

	}

	public void saveItemsToCart() {

		cartService.saveItemsToCart(beersToCart, cartService.findByUser(sessionManager.getLoggedInUser()));

		FacesMessageTool.createInfoMessage("Termékek a kosárba helyezve.");
		resetCart();
	}

	/**
	 * Visszaadja az adatbázisban szereplő sörök listáját.
	 * 
	 * @return az adatbázisban szereplő sörök listája.
	 */
	public List<Beer> getBeersInShop() {
		return beersInShop;
	}

	/**
	 * Beállítja a megjelenítendő sörök listáját.
	 * 
	 * @param beersInShop
	 *            a megjelenítendő sörök.
	 */
	public void setBeersInShop(List<Beer> beersInShop) {
		this.beersInShop = beersInShop;
	}

	/**
	 * Visszaadja a kiválasztott sört a lisátból.
	 * 
	 * @return a kiválasztott sör.
	 */
	public Beer getSelectedBeer() {
		return selectedBeer;
	}

	/**
	 * Beállítja a kiválasztott sört.
	 * 
	 * @param selectedBeer
	 *            a kiválsztott sör.
	 */
	public void setSelectedBeer(Beer selectedBeer) {
		this.selectedBeer = selectedBeer;
	}

	public Map<Beer, Integer> getBeersInCart() {
		return beersToCart;
	}

	public void setBeersInCart(Map<Beer, Integer> beersInCart) {
		this.beersToCart = beersInCart;
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

}
