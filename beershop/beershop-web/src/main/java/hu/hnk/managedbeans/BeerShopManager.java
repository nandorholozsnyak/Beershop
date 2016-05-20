package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.hnk.beershop.exception.NegativeQuantityNumberException;
import hu.hnk.beershop.exception.StorageItemQuantityExceededException;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.service.interfaces.BeerService;
import hu.hnk.beershop.service.interfaces.CartService;
import hu.hnk.beershop.service.interfaces.StorageService;
import hu.hnk.loginservices.SessionManager;
import hu.hnk.tool.FacesMessageTool;

/**
 * A sörshop áruházi oldalán lévő termékek megjelenítését és különböző
 * lehetőségeit végző bean.
 * 
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
	public static final Logger logger = LoggerFactory.getLogger(BeerShopManager.class);

	/**
	 * Loggolási konstans, ha adatbázis hiba van.
	 */
	private static final String COULD_NOT_LOAD_BEERS = "Could not load beers.";

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

	/**
	 * A munkamenetet kezelő szolgáltatás.
	 */
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

	/**
	 * A kosárba helyezendő sör - darabszám párokat tartalamzó objektum.
	 */
	private Map<Beer, Integer> beersToCart;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
	@PostConstruct
	public void init() {
		logger.info("beerShopManager init");
		try {
			setBeersInShop(beerService.findAll());
		} catch (Exception e) {
			logger.warn(COULD_NOT_LOAD_BEERS);
			logger.error(e.getMessage(), e);
		}
		resetCart();
	}

	/**
	 * A kosárba helyezendő objektum ürítése.
	 */
	private void resetCart() {
		beersToCart = new HashMap<>();
		for (Beer b : beersInShop) {
			beersToCart.put(b, 0);
		}

		// UI frissítése
		for (int i = 0; i < beersInShop.size(); i++) {
			FacesContext.getCurrentInstance()
					.getPartialViewContext()
					.getRenderIds()
					.add("form:beers:" + i + ":beerQuantity");
		}
	}

	/**
	 * A paraméterül kapott darabszámának növelése a kosárba helyezés előtt.
	 * 
	 * @param beer
	 *            a kiválasztott sör
	 */
	public void incrementBeer(Beer beer) {
		Integer quantity = beersToCart.get(beer);
		List<StorageItem> items = null;
		try {
			items = storageService.findAll();
		} catch (Exception e) {
			logger.warn(COULD_NOT_LOAD_BEERS);
			logger.error(e.getMessage(), e);
		}

		try {
			storageService.checkStorageItemQuantityLimit(items, beer, ++quantity);
			beersToCart.put(beer, quantity);
		} catch (StorageItemQuantityExceededException e) {
			logger.error(e.getMessage(), e);

			beersToCart.put(beer, items.stream()
					.filter(p -> p.getBeer()
							.equals(beer))
					.findFirst()
					.get()
					.getQuantity());
			FacesMessageTool.createInfoMessage("A raktár maximumát elérte.");
		} catch (NegativeQuantityNumberException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			FacesMessageTool.createErrorMessage("Adatbázishiba történt.");
			logger.warn(COULD_NOT_LOAD_BEERS);
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * A paraméterül kapott sör darabszámának csökkentése a kosárba helyezés
	 * előtt.
	 * 
	 * @param beer
	 *            a kiválasztott sör
	 */
	public void decreaseBeer(Beer beer) {

		Integer quantity = beersToCart.get(beer);
		List<StorageItem> items = null;
		try {
			items = storageService.findAll();
		} catch (Exception e) {
			logger.warn("Could not load beers.");
			logger.error(e.getMessage(), e);
		}
		try {
			storageService.checkStorageItemQuantityLimit(storageService.findAll(), beer, --quantity);
			beersToCart.put(beer, quantity);
		} catch (StorageItemQuantityExceededException e) {
			logger.error(e.getMessage(), e);
			beersToCart.put(beer, items.stream()
					.filter(p -> p.getBeer()
							.equals(beer))
					.findFirst()
					.get()
					.getQuantity());
		} catch (NegativeQuantityNumberException e) {
			logger.error(e.getMessage(), e);
			FacesMessageTool.createWarnMessage("A darabszám nem lehet negatív érték!");
		} catch (Exception e) {
			FacesMessageTool.createErrorMessage("Adatbázishiba történt.");
			logger.warn(COULD_NOT_LOAD_BEERS);
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * A kiválasztott termékek kosárba való helyezése.
	 */
	public void saveItemsToCart() {

		try {
			cartService.saveItemsToCart(beersToCart, cartService.findByUser(sessionManager.getLoggedInUser()));
			FacesMessageTool.createInfoMessage("Termékek a kosárba helyezve.");
			resetCart();
		} catch (Exception e) {
			FacesMessageTool.createErrorMessage("Adatbázishiba történt.");
			logger.warn(COULD_NOT_LOAD_BEERS);
			logger.error(e.getMessage(), e);
		}

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

	/**
	 * Visszaadja a sörök kosárba helyezésekor a megfelelő választott
	 * mennyiséget leíró objektumot.
	 * 
	 * @return a sörök darabszámmal meghatározott objektuma.
	 */
	public Map<Beer, Integer> getBeersInCart() {
		return beersToCart;
	}

	/**
	 * Beállítja a sörök darabszámmal létrejött állapotát.
	 * 
	 * @param beersInCart
	 *            a sörök darabszámmal létrejött objektuma.
	 */
	public void setBeersInCart(Map<Beer, Integer> beersInCart) {
		this.beersToCart = beersInCart;
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

}
