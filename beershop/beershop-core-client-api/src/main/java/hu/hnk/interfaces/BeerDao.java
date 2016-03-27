package hu.hnk.interfaces;

import java.util.List;

import hu.hnk.beershop.model.Beer;

public interface BeerDao {
	/**
	 * Az összes sör lekérdezése az adatbázisból.
	 * 
	 * @return a sörök listája
	 */
	public List<Beer> findAll();
}
