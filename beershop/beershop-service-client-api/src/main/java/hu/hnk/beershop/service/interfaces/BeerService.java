package hu.hnk.beershop.service.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Beer;

public interface BeerService {
	/**
	 * Az összes sör lekérdezése.
	 * 
	 * @return a sörök listája
	 */
	public List<Beer> findAll();

}
