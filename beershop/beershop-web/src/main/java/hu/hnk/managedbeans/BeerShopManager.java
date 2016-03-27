package hu.hnk.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import hu.hnk.beershop.model.Beer;
import hu.hnk.beershop.service.interfaces.BeerService;

@ManagedBean(name = "beerShopManager")
@ViewScoped
public class BeerShopManager implements Serializable {

	@EJB
	BeerService beerService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private List<Beer> beersInShop;

	private Beer selectedBeer;

	@PostConstruct
	public void init() {
		setBeersInShop(beerService.findAll());
	}

	public List<Beer> getBeersInShop() {
		return beersInShop;
	}

	public void setBeersInShop(List<Beer> beersInShop) {
		this.beersInShop = beersInShop;
	}

	public Beer getSelectedBeer() {
		return selectedBeer;
	}

	public void setSelectedBeer(Beer selectedBeer) {
		this.selectedBeer = selectedBeer;
	}

}
