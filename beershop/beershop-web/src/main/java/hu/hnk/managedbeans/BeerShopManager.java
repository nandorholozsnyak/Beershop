package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import hu.hnk.beershop.exception.NegativeQuantityNumber;
import hu.hnk.beershop.exception.StorageItemQuantityExceeded;
import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.model.StorageItem;
import hu.hnk.beershop.service.interfaces.BeerService;
import hu.hnk.beershop.service.interfaces.StorageService;

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
	 * A söröket kezelõ szolgáltatás.
	 */
	@EJB
	private BeerService beerService;

	/**
	 * A raktárt kezelõ szolgáltatás.
	 */
	@EJB
	private StorageService storageService;

	/**
	 * A sörök listája.
	 */
	private List<Beer> beersInShop;

	/**
	 * A kiválasztott sör.
	 */
	private Beer selectedBeer;

	private Map<Beer, Integer> beersToCart;

	FacesMessage msg;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
	@PostConstruct
	public void init() {
		setBeersInShop(beerService.findAll());
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
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "A raktár maximumát elérte.",
					"A raktár maximumát elérte.");
			beersToCart.put(beer, items.stream().filter(p -> p.getBeer().equals(beer)).findFirst().get().getQuantity());
		} catch (NegativeQuantityNumber e) {
			logger.warn(e.getMessage());
		}

		if (msg != null) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
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
			beersToCart.put(beer, items.stream().filter(p -> p.getBeer().equals(beer)).findFirst().get().getQuantity());
		} catch (NegativeQuantityNumber e) {
			logger.warn(e.getMessage());
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "A darabszám nem lehet negatív érték!",
					"A darabszám nem lehet negatív érték!");
		}

		if (msg != null) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	/**
	 * Visszaadja az adatbázisban szereplõ sörök listáját.
	 * 
	 * @return az adatbázisban szereplõ sörök listája.
	 */
	public List<Beer> getBeersInShop() {
		return beersInShop;
	}

	/**
	 * Beállítja a megjelenítendõ sörök listáját.
	 * 
	 * @param beersInShop
	 *            a megjelenítendõ sörök.
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

}
