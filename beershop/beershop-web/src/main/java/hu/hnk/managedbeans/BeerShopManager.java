package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.service.interfaces.BeerService;

/**
 * @author Nandi
 *
 */
@ManagedBean(name = "beerShopManager")
@ViewScoped
public class BeerShopManager implements Serializable {
	/**
	 * A söröket kezelõ szolgáltatás.
	 */
	@EJB
	private BeerService beerService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A sörök listája.
	 */
	private List<Beer> beersInShop;

	private Beer beerCounterSelector;

	/**
	 * A kiválasztott sör.
	 */
	private Beer selectedBeer;

	private Map<Beer, Integer> beersInCart;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
	@PostConstruct
	public void init() {
		setBeersInShop(beerService.findAll());
		beersInCart = new HashMap<>();
		for (Beer b : beersInShop) {
			beersInCart.put(b, 0);
		}
	}

	public void updateBeerCounter(AjaxBehaviorEvent event) {
		for (Beer b : beersInShop) {
			System.out.println(beersInCart.get(b));
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
		return beersInCart;
	}

	public void setBeersInCart(Map<Beer, Integer> beersInCart) {
		this.beersInCart = beersInCart;
	}

	public Beer getBeerCounterSelector() {
		return beerCounterSelector;
	}

	public void setBeerCounterSelector(Beer beerCounterSelector) {
		this.beerCounterSelector = beerCounterSelector;
	}

}
