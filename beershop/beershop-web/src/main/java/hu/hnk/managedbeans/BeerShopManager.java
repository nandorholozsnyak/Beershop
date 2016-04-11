package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
	BeerService beerService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A sörök listája.
	 */
	private List<Beer> beersInShop;

	/**
	 * A kiválasztott sör.
	 */
	private Beer selectedBeer;

	/**
	 * Inicializáló metódus, a managed bean létrejöttekor.
	 */
	@PostConstruct
	public void init() {
		setBeersInShop(beerService.findAll());
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

}
